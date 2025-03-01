package cr.co.ctpcit.citsacbackend.logic.services.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import cr.co.ctpcit.citsacbackend.data.repositories.configs.SystemConfigRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.SystemConfigDto;
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
    saveWeight(Configurations.PREV_GRADES_WEIGHT, prevGradesWeight);

    //Update ACADEMIC_WEIGHT
    saveWeight(Configurations.ACADEMIC_WEIGHT, academicWeight);

    //Update ENGLISH_WEIGHT
    saveWeight(Configurations.ENGLISH_WEIGHT, englishWeight);
  }

  private void saveWeight(Configurations configName, Double weight) {
    SystemConfigEntity savedWeight =
        systemConfigRepository.findByConfigName(configName).orElse(null);

    if (savedWeight == null) {
      savedWeight = new SystemConfigEntity(null, configName, String.valueOf(weight));
    } else {
      savedWeight.setConfigValue(String.valueOf(weight));
    }

    systemConfigRepository.save(savedWeight);
  }

  @Override
  public List<SystemConfigEntity> getNotifications(Configurations configName) {
    return List.of();
  }

  @Override
  public void updateNotifications(String emailContact, String emailNotificationsContact,
      String whatsappContact, String officeContact, String instagramContact,
      String facebookContact) {


  }

  /*@Override
  public List<SystemConfigEntity> getNotifications(Configurations configName) {
    List<SystemConfigEntity> notifications =
        systemConfigRepository.getSystemConfigEntitiesByConfigNameContaining(configName);

    if (notifications.isEmpty()) {
      systemConfigRepository.updateNotifications("", "", "", "", "", "");
      notifications =
          systemConfigRepository.getSystemConfigEntitiesByConfigNameContaining(configName);
    }

    return notifications;
  }*/

  /*@Override
  public void updateNotifications(String emailContact, String emailNotificationsContact,
      String whatsappContact, String officeContact, String instagramContact,
      String facebookContact) {
    try {
      systemConfigRepository.updateNotifications(emailContact, emailNotificationsContact,
          whatsappContact, officeContact, instagramContact, facebookContact);
    } catch (Exception e) {
      throw new RuntimeException("Error al actualizar las notificaciones", e);
    }
  }*/
}
