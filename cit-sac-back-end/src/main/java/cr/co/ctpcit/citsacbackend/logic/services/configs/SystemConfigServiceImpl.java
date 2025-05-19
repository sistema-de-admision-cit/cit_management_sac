package cr.co.ctpcit.citsacbackend.logic.services.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.ExamDayEntity;
import cr.co.ctpcit.citsacbackend.data.entities.configs.ExamPeriodEntity;
import cr.co.ctpcit.citsacbackend.data.entities.configs.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import cr.co.ctpcit.citsacbackend.data.repositories.configs.ExamPeriodRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.configs.SystemConfigRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.ExamPeriodDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.SystemConfigDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.UpdateContactInfoConfigsDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.configs.ExamPeriodMapper;
import cr.co.ctpcit.citsacbackend.logic.mappers.configs.SystemConfigMapper;
import cr.co.ctpcit.citsacbackend.data.utils.EncryptionUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Implementation of the SystemConfigService interface. This service provides methods for managing
 * system configurations, including question quantities, process weights, contact information, and
 * exam periods. It uses a cache to store configuration values for faster access.
 */
@RequiredArgsConstructor
@Service
public class SystemConfigServiceImpl implements SystemConfigService {

  /**
   * Repository for accessing system configuration entities.
   */
  private final SystemConfigRepository systemConfigRepository;

  /**
   * Repository for accessing exam period entities.
   */
  private final ExamPeriodRepository examPeriodRepository;

  /**
   * Cache for storing system configuration entities for faster access. The key is the configuration
   * name and the value is the configuration entity.
   */
  private final Map<String, SystemConfigEntity> cache = new ConcurrentHashMap<>();

  /**
   * Utility for encrypting and decrypting sensitive configuration values.
   */
  private final EncryptionUtil encryptionUtil;

  /**
   * Loads all system configurations from the database into the cache. This method is called
   * automatically after the bean is constructed.
   */
  @PostConstruct
  public void loadConfigFromDB() {
    ConfigDataInitializer configDataInitializer = new ConfigDataInitializer(systemConfigRepository,
            encryptionUtil);
    configDataInitializer.initConfigs();

    systemConfigRepository.findAll()
            .forEach(config -> cache.put(config.getConfigName().name(), config));

    if (!cache.containsKey(Configurations.WHATSAPP_API_KEY.name())) {
      String whatsappApiKey = encryptionUtil.encrypt("WhatsappApiKeyDeEjemplo");
      SystemConfigEntity config =
              new SystemConfigEntity(null, Configurations.WHATSAPP_API_KEY, whatsappApiKey);
      systemConfigRepository.save(config);
      cache.put(Configurations.WHATSAPP_API_KEY.name(), config);
    }

    if (!cache.containsKey(Configurations.EMAIL_PASSWORD.name())) {
      String emailPassword = encryptionUtil.encrypt("qipy hnvl cliv slqy");
      SystemConfigEntity config =
              new SystemConfigEntity(null, Configurations.EMAIL_PASSWORD, emailPassword);
      systemConfigRepository.save(config);
      cache.put(Configurations.EMAIL_PASSWORD.name(), config);
    }
  }

  /**
   * Retrieves the configured question quantities for different exam types.
   *
   * @return a list of system configuration DTOs containing question quantities for DAI and academic
   * exams
   */
  @Override
  public List<SystemConfigDto> getQuestionsQuantity() {
    return getConfigList(Configurations.DAI_EXAM_QUESTIONS_QUANTITY,
            Configurations.ACADEMIC_EXAM_QUESTIONS_QUANTITY);
  }

  /**
   * Updates the question quantities for different exam types.
   *
   * @param daiQuestionsQuantity      the new number of questions for DAI exams
   * @param academicQuestionsQuantity the new number of questions for academic exams
   * @throws ResponseStatusException if any of the quantities is negative
   */
  @Override
  public void updateQuantity(int daiQuestionsQuantity, int academicQuestionsQuantity) {

    if (daiQuestionsQuantity < 0 || academicQuestionsQuantity < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
              "La cantidad de preguntas debe ser mayor o igual a 0");
    }

    //Update DAI_EXAM_QUESTIONS_QUANTITY
    saveConfiguration(Configurations.DAI_EXAM_QUESTIONS_QUANTITY,
            String.valueOf(daiQuestionsQuantity), false);

    //Update ACADEMIC_EXAM_QUESTIONS_QUANTITY
    saveConfiguration(Configurations.ACADEMIC_EXAM_QUESTIONS_QUANTITY,
            String.valueOf(academicQuestionsQuantity), false);

  }

  /**
   * Retrieves the configured process weights for different evaluation components.
   *
   * @return a list of system configuration DTOs containing weights for previous grades, academic,
   * and English components
   */
  @Override
  public List<SystemConfigDto> getProcessWeights() {
    return getConfigList(Configurations.PREV_GRADES_WEIGHT, Configurations.ACADEMIC_WEIGHT);
  }

  /**
   * Updates the process weights for different evaluation components. The sum of all weights must
   * equal 1.
   *
   * @param prevGradesWeight the weight for previous grades component
   * @param academicWeight   the weight for academic component
   * @throws ResponseStatusException if the sum of weights is not equal to 1
   */
  @Override
  public void updateWeights(Double prevGradesWeight, Double academicWeight) {
    //Validate weights sum equals 1
    if (prevGradesWeight + academicWeight != 1) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La suma de los pesos debe ser 1");
    }

    //Update PREV_GRADES_WEIGHT
    saveConfiguration(Configurations.PREV_GRADES_WEIGHT, String.valueOf(prevGradesWeight), false);

    //Update ACADEMIC_WEIGHT
    saveConfiguration(Configurations.ACADEMIC_WEIGHT, String.valueOf(academicWeight), false);

  }


  /**
   * Retrieves the configured contact information.
   *
   * @return a list of system configuration DTOs containing various contact information
   */
  @Override
  public List<SystemConfigDto> getContactInfo() {
    return getConfigList(Configurations.EMAIL_CONTACT, Configurations.EMAIL_NOTIFICATION_CONTACT,
            Configurations.WHATSAPP_CONTACT, Configurations.OFFICE_CONTACT,
            Configurations.INSTAGRAM_CONTACT, Configurations.FACEBOOK_CONTACT,
            Configurations.EMAIL_PASSWORD, Configurations.WHATSAPP_API_KEY);
  }

  /**
   * Updates the contact information with the provided values. Some values are stored as sensitive
   * information and will be encrypted.
   *
   * @param contactInfoConfigsDto DTO containing the new contact information values
   */
  @Override
  public void updateContactInfo(UpdateContactInfoConfigsDto contactInfoConfigsDto) {
    //Update EMAIL_CONTACT
    saveConfiguration(Configurations.EMAIL_CONTACT, contactInfoConfigsDto.emailContact(), false);

    //Update EMAIL_NOTIFICATIONS_CONTACT
    saveConfiguration(Configurations.EMAIL_NOTIFICATION_CONTACT,
            contactInfoConfigsDto.emailNotificationsContact(), false);

    //Update WHATSAPP_CONTACT
    saveConfiguration(Configurations.WHATSAPP_CONTACT, contactInfoConfigsDto.whatsappContact(),
            false);

    //Update OFFICE_CONTACT
    saveConfiguration(Configurations.OFFICE_CONTACT, contactInfoConfigsDto.officeContact(), false);

    //Update INSTAGRAM_CONTACT
    saveConfiguration(Configurations.INSTAGRAM_CONTACT, contactInfoConfigsDto.instagramContact(),
            false);

    //Update FACEBOOK_CONTACT
    saveConfiguration(Configurations.FACEBOOK_CONTACT, contactInfoConfigsDto.facebookContact(),
            false);

    //Update EMAIL_PASSWORD (sensitive)
    saveConfiguration(Configurations.EMAIL_PASSWORD, contactInfoConfigsDto.emailPassword(), true);

    //Update WHATSAPP_API_KEY (sensitive)
    saveConfiguration(Configurations.WHATSAPP_API_KEY, contactInfoConfigsDto.whatsappApiKey(),
            true);
  }

  /**
   * Retrieves an exam period by its ID.
   *
   * @param id the ID of the exam period to retrieve
   * @return the exam period DTO
   * @throws ResponseStatusException if the exam period is not found
   */
  @Override
  public ExamPeriodDto getExamPeriod(Long id) {
    return ExamPeriodMapper.toDto(examPeriodRepository.findById(id).orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Periodo de exámenes no encontrado")));
  }

  /**
   * Retrieves all exam periods for the current year.
   *
   * @return a list of exam period DTOs for the current year
   */
  @Override
  public List<ExamPeriodDto> getCurrentExamPeriods() {
    int currentYear = LocalDate.now().getYear();
    return ExamPeriodMapper.periodsToDtoList(examPeriodRepository.findByYear(currentYear));
  }

  /**
   * Retrieves all exam periods for a specific year.
   *
   * @param year the year to retrieve exam periods for
   * @return a list of exam period DTOs for the specified year
   */
  @Override
  public List<ExamPeriodDto> getExamPeriodsByYear(int year) {
    return ExamPeriodMapper.periodsToDtoList(examPeriodRepository.findByYear(year));
  }

  /**
   * Creates a new exam period with its associated exam days. Validates that the exam period doesn't
   * already exist and doesn't overlap with existing periods.
   *
   * @param examPeriodDto the exam period DTO containing the period details and exam days
   * @throws ResponseStatusException if the exam period already exists or overlaps with another
   *                                 period
   */
  @Override
  public void createExamPeriod(ExamPeriodDto examPeriodDto) {
    //Validate if the exam period already exists
    if (examPeriodRepository.existsByStartDateAndEndDate(examPeriodDto.startDate(),
            examPeriodDto.endDate())) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "El periodo de exámenes ya existe");
    }

    //Validate if the period overlap another period
    List<ExamPeriodEntity> periods =
            examPeriodRepository.findByStartDateBetweenOrEndDateBetween(examPeriodDto.startDate(),
                    examPeriodDto.endDate());
    if (!periods.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT,
              "El periodo de exámenes se superpone con otro periodo ya existente");
    }

    //Create the exam period
    ExamPeriodEntity examPeriodEntity = ExamPeriodMapper.toEntity(examPeriodDto);

    //Create the exam days
    List<ExamDayEntity> examDays = ExamPeriodMapper.daysDtoToEntityList(examPeriodDto.examDays());

    //Add exam days to the period
    for (ExamDayEntity examDay : examDays) {
      examPeriodEntity.addExamDay(examDay);
    }

    //save the exam period
    examPeriodRepository.save(examPeriodEntity);
  }

  /**
   * Updates the number of questions for a specific exam type.
   *
   * @param config   the configuration to update (must be ACADEMIC_EXAM_QUESTIONS_QUANTITY or
   *                 DAI_EXAM_QUESTIONS_QUANTITY)
   * @param quantity the new number of questions
   * @throws ResponseStatusException if the configuration is not valid for exam questions quantity
   */
  @Override
  public void updateExamQuestionsQuantity(Configurations config, Integer quantity) {
    if (config != Configurations.ACADEMIC_EXAM_QUESTIONS_QUANTITY && config != Configurations.DAI_EXAM_QUESTIONS_QUANTITY) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Configuración no válida");
    }

    saveConfiguration(config, String.valueOf(quantity), false);
  }

  /**
   * Retrieves the value of a configuration. If the configuration is marked as sensitive, the value
   * will be decrypted before returning.
   *
   * @param configName the name of the configuration to retrieve
   * @param isSensible whether the configuration value is sensitive and needs decryption
   * @return the configuration value (decrypted if sensitive)
   */
  @Override
  public String getConfigValue(Configurations configName, boolean isSensible) {
    SystemConfigEntity config = systemConfigRepository.findByConfigName(configName)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Configuración no encontrada: " + configName.name()));

    if (isSensible) {
      String decryptedValue = encryptionUtil.decrypt(config.getConfigValue());
      return decryptedValue;
    }

    return config.getConfigValue();
  }

  /**
   * Deletes an exam period by its ID.
   *
   * @param id the ID of the exam period to delete
   */
  @Override
  public void deleteExamPeriod(Long id) {
    examPeriodRepository.deleteById(id);
  }

  /**
   * Saves a configuration value to the database and updates the cache. If the configuration is
   * marked as sensitive, the value will be encrypted before saving.
   *
   * @param configName the name of the configuration to save
   * @param value      the value to save
   * @param isSensible whether the configuration value is sensitive and needs encryption
   */
  private void saveConfiguration(Configurations configName, String value, boolean isSensible) {
    if (isSensible) {
      value = encryptionUtil.encrypt(value);
    }
    SystemConfigEntity config = cache.get(configName.name());
    config.setConfigValue(value);
    systemConfigRepository.updateByConfigName(configName, value);
    cache.put(configName.name(), config);
  }

  /**
   * Retrieves a list of configuration DTOs for the specified configuration names. Configurations
   * that don't exist in the cache will be filtered out.
   *
   * @param configNames the names of the configurations to retrieve
   * @return a list of system configuration DTOs
   */
  private List<SystemConfigDto> getConfigList(Configurations... configNames) {
    List<SystemConfigEntity> configs = new ArrayList<>();
    for (Configurations configName : configNames) {
      SystemConfigEntity config = cache.get(configName.name());
      if (config != null) {
        configs.add(config);
      }
    }

    configs =
            configs.stream().filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));

    return SystemConfigMapper.toDtoList(configs);
  }
}
