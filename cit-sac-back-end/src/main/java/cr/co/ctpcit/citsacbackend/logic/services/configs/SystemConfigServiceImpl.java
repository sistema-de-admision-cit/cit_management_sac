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
import cr.co.ctpcit.citsacbackend.logic.services.EncryptionUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Service
public class SystemConfigServiceImpl implements SystemConfigService {

  private final SystemConfigRepository systemConfigRepository;
  private final ExamPeriodRepository examPeriodRepository;
  private final Map<String, SystemConfigEntity> cache = new ConcurrentHashMap<>();
  private final EncryptionUtil encryptionUtil;

  @PostConstruct
  public void loadConfigFromDB() {
    systemConfigRepository.findAll()
        .forEach(config -> cache.put(config.getConfigName().name(), config));
  }

  @Override
  public List<SystemConfigDto> getQuestionsQuantity() {
    return getConfigList(Configurations.DAI_EXAM_QUESTIONS_QUANTITY,
        Configurations.ACADEMIC_EXAM_QUESTIONS_QUANTITY);
  }

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

  @Override
  public List<SystemConfigDto> getProcessWeights() {
    return getConfigList(Configurations.PREV_GRADES_WEIGHT, Configurations.ACADEMIC_WEIGHT,
        Configurations.ENGLISH_WEIGHT);
  }

  @Override
  public void updateWeights(Double prevGradesWeight, Double academicWeight, Double englishWeight) {
    //Validate weights sum equals 1
    if (prevGradesWeight + academicWeight + englishWeight != 1) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La suma de los pesos debe ser 1");
    }

    //Update PREV_GRADES_WEIGHT
    saveConfiguration(Configurations.PREV_GRADES_WEIGHT, String.valueOf(prevGradesWeight), false);

    //Update ACADEMIC_WEIGHT
    saveConfiguration(Configurations.ACADEMIC_WEIGHT, String.valueOf(academicWeight), false);

    //Update ENGLISH_WEIGHT
    saveConfiguration(Configurations.ENGLISH_WEIGHT, String.valueOf(englishWeight), false);
  }


  @Override
  public List<SystemConfigDto> getContactInfo() {
    return getConfigList(Configurations.EMAIL_CONTACT, Configurations.EMAIL_NOTIFICATION_CONTACT,
        Configurations.WHATSAPP_CONTACT, Configurations.OFFICE_CONTACT,
        Configurations.INSTAGRAM_CONTACT, Configurations.FACEBOOK_CONTACT, Configurations.EMAIL_PASSWORD, Configurations.WHATSAPP_API_KEY);
  }

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

    //Update FACEBOOK_CONTACT
    saveConfiguration(Configurations.EMAIL_PASSWORD, contactInfoConfigsDto.emailPassword(),
            true);

    //Update FACEBOOK_CONTACT
    saveConfiguration(Configurations.WHATSAPP_API_KEY, contactInfoConfigsDto.whatsappApiKey()
            ,
            true);
  }

  @Override
  public ExamPeriodDto getExamPeriod(Long id) {
    return ExamPeriodMapper.toDto(examPeriodRepository.findById(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Periodo de exámenes no encontrado")));
  }

  @Override
  public List<ExamPeriodDto> getCurrentExamPeriods() {
    int currentYear = LocalDate.now().getYear();
    return ExamPeriodMapper.periodsToDtoList(examPeriodRepository.findByYear(currentYear));
  }

  @Override
  public List<ExamPeriodDto> getExamPeriodsByYear(int year) {
    return ExamPeriodMapper.periodsToDtoList(examPeriodRepository.findByYear(year));
  }

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

  @Override
  public void updateExamQuestionsQuantity(Configurations config, Integer quantity) {
    if (config != Configurations.ACADEMIC_EXAM_QUESTIONS_QUANTITY && config != Configurations.DAI_EXAM_QUESTIONS_QUANTITY) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Configuración no válida");
    }

    saveConfiguration(config, String.valueOf(quantity), false);
  }

  @Override
  public String getConfigValue(Configurations configName, boolean isSensible) {
    SystemConfigEntity config = cache.get(configName.name());
    if (isSensible) {
      return encryptionUtil.decrypt(config.getConfigValue());
    }

    return config.getConfigValue();
  }

  private void saveConfiguration(Configurations configName, String value, boolean isSensible) {
    if (isSensible) {
      value = encryptionUtil.encrypt(value);
    }
    SystemConfigEntity config = cache.get(configName.name());
    config.setConfigValue(value);
    systemConfigRepository.updateByConfigName(configName, value);
    cache.put(configName.name(), config);
  }

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
