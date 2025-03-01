package cr.co.ctpcit.citsacbackend.logic.services.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import cr.co.ctpcit.citsacbackend.data.repositories.configs.SystemConfigRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.SystemConfigDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.UpdateContactInfoConfigsDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.configs.SystemConfigMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SystemConfigServiceImpl implements SystemConfigService {

  private final SystemConfigRepository systemConfigRepository;

  @Override
  public SystemConfigDto addSystemConfig(SystemConfigDto systemConfigDto) {
    //Find existing configuration
    SystemConfigEntity savedConfig =
        systemConfigRepository.findByConfigName(systemConfigDto.configName()).orElse(null);

    SystemConfigEntity savedEntity;
    //If configuration does not exist, create it, else update it
    if (savedConfig == null) {
      SystemConfigEntity newConfig = SystemConfigMapper.toEntity(systemConfigDto);
      savedEntity = systemConfigRepository.save(newConfig);
    } else {
      savedConfig.setConfigValue(systemConfigDto.configValue());
      savedEntity = systemConfigRepository.save(savedConfig);
    }

    return SystemConfigMapper.toDto(savedEntity);
  }

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
