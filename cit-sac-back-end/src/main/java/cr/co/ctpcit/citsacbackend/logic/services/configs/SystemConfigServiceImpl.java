package cr.co.ctpcit.citsacbackend.logic.services.configs;

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

@RequiredArgsConstructor
@Service
public class SystemConfigServiceImpl implements SystemConfigService {

  private final SystemConfigRepository systemConfigRepository;
  private final ExamPeriodRepository examPeriodRepository;

  @Override
  public List<SystemConfigDto> getProcessWeights() {
    return SystemConfigMapper.toDtoList(systemConfigRepository.getProcessWeights());
  }

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

  @Override
  public List<SystemConfigDto> getContactInfo() {
    return SystemConfigMapper.toDtoList(systemConfigRepository.getContactInfo());
  }

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

  @Override
  public List<ExamPeriodDto> getCurrentExamPeriods() {
    int currentYear = LocalDate.now().getYear();
    return ExamPeriodMapper.examPeriodToDtoList(examPeriodRepository.findByYear(currentYear));
  }

  private void saveConfiguration(Configurations configName, String value) {
    SystemConfigEntity savedWeight =
        systemConfigRepository.findByConfigName(configName).orElse(null);

    if (savedWeight == null) {
      savedWeight = new SystemConfigEntity(null, configName, value);
    } else {
      savedWeight.setConfigValue(value);
    }

    systemConfigRepository.save(savedWeight);
  }
}
