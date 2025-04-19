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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
/**
 * Implementation of the SystemConfigService interface.
 * Provides concrete functionality for managing system configurations including:
 * - Exam question quantities
 * - Evaluation process weights
 * - Contact information
 * - Exam periods
 */
@RequiredArgsConstructor
@Service
public class SystemConfigServiceImpl implements SystemConfigService {

  private final SystemConfigRepository systemConfigRepository;
  private final ExamPeriodRepository examPeriodRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  public List<SystemConfigDto> getQuestionsQuantity() {
    return SystemConfigMapper.toDtoList(systemConfigRepository.getQuestionsQuantity());
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public void updateQuantity(int daiQuestionsQuantity, int academicQuestionsQuantity) {

    if (daiQuestionsQuantity < 0 || academicQuestionsQuantity < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad de preguntas debe ser mayor o igual a 0");
    }

    //Update DAI_EXAM_QUESTIONS_QUANTITY
    saveConfiguration(Configurations.DAI_EXAM_QUESTIONS_QUANTITY, String.valueOf(daiQuestionsQuantity));

    //Update ACADEMIC_EXAM_QUESTIONS_QUANTITY
    saveConfiguration(Configurations.ACADEMIC_EXAM_QUESTIONS_QUANTITY, String.valueOf(academicQuestionsQuantity));

  }
  /**
   * {@inheritDoc}
   */
  @Override
  public List<SystemConfigDto> getProcessWeights() {
    return SystemConfigMapper.toDtoList(systemConfigRepository.getProcessWeights());
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public void updateWeights(Double prevGradesWeight, Double academicWeight, Double englishWeight) {
    //Validate weights sum equals 1
    if (prevGradesWeight + academicWeight + englishWeight != 1) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La suma de los pesos debe ser 1");
    }

    //Update PREV_GRADES_WEIGHT
    saveConfiguration(Configurations.PREV_GRADES_WEIGHT, String.valueOf(prevGradesWeight));

    //Update ACADEMIC_WEIGHT
    saveConfiguration(Configurations.ACADEMIC_WEIGHT, String.valueOf(academicWeight));

    //Update ENGLISH_WEIGHT
    saveConfiguration(Configurations.ENGLISH_WEIGHT, String.valueOf(englishWeight));
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public List<SystemConfigDto> getContactInfo() {
    return SystemConfigMapper.toDtoList(systemConfigRepository.getContactInfo());
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public void updateContactInfo(UpdateContactInfoConfigsDto contactInfoConfigsDto) {
    //Update EMAIL_CONTACT
    saveConfiguration(Configurations.EMAIL_CONTACT, contactInfoConfigsDto.emailContact());

    //Update EMAIL_NOTIFICATIONS_CONTACT
    saveConfiguration(Configurations.EMAIL_NOTIFICATION_CONTACT,
        contactInfoConfigsDto.emailNotificationsContact());

    //Update WHATSAPP_CONTACT
    saveConfiguration(Configurations.WHATSAPP_CONTACT, contactInfoConfigsDto.whatsappContact());

    //Update OFFICE_CONTACT
    saveConfiguration(Configurations.OFFICE_CONTACT, contactInfoConfigsDto.officeContact());

    //Update INSTAGRAM_CONTACT
    saveConfiguration(Configurations.INSTAGRAM_CONTACT, contactInfoConfigsDto.instagramContact());

    //Update FACEBOOK_CONTACT
    saveConfiguration(Configurations.FACEBOOK_CONTACT, contactInfoConfigsDto.facebookContact());
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public ExamPeriodDto getExamPeriod(Long id) {
    return ExamPeriodMapper.toDto(examPeriodRepository.findById(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Periodo de exámenes no encontrado")));
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public List<ExamPeriodDto> getCurrentExamPeriods() {
    int currentYear = LocalDate.now().getYear();
    return ExamPeriodMapper.periodsToDtoList(examPeriodRepository.findByYear(currentYear));
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public List<ExamPeriodDto> getExamPeriodsByYear(int year) {
    return ExamPeriodMapper.periodsToDtoList(examPeriodRepository.findByYear(year));
  }
  /**
   * {@inheritDoc}
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
   * {@inheritDoc}
   */
  @Override
  public void updateExamQuestionsQuantity(Configurations config, Integer quantity) {
    if (config != Configurations.ACADEMIC_EXAM_QUESTIONS_QUANTITY && config != Configurations.DAI_EXAM_QUESTIONS_QUANTITY) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Configuración no válida");
    }

    saveConfiguration(config, String.valueOf(quantity));
  }
  /**
   * Internal method to save or update a configuration value.
   *
   * @param configName the configuration name/enum
   * @param value the value to be saved
   */
  private void saveConfiguration(Configurations configName, String value) {
    SystemConfigEntity savedConfig =
        systemConfigRepository.findByConfigName(configName).orElse(null);

    if (savedConfig == null) {
      savedConfig = new SystemConfigEntity(null, configName, value);
    } else {
      savedConfig.setConfigValue(value);
    }

    systemConfigRepository.save(savedConfig);
  }
}
