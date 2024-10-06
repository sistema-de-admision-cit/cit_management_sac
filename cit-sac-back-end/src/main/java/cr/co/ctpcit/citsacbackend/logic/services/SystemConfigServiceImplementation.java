package cr.co.ctpcit.citsacbackend.logic.services;

import cr.co.ctpcit.citsacbackend.data.entities.config.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.config.SystemConfigRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.config.SystemConfigDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.config.SystemConfigMapper;
import cr.co.ctpcit.citsacbackend.logic.services.config.SystemConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SystemConfigServiceImplementation implements SystemConfigService {

  private final SystemConfigRepository systemConfigRepository;

  @Override
  public SystemConfigDto addSystemConfig(SystemConfigDto systemConfigDto) {
    SystemConfigEntity systemConfigEntity = SystemConfigMapper.toEntity(systemConfigDto);
    SystemConfigEntity savedEntity = systemConfigRepository.save(systemConfigEntity);
    return SystemConfigMapper.toDto(savedEntity);
  }

  @Override
  public List<SystemConfigEntity> getExamsPercentages(String configName) {
    return systemConfigRepository.getSystemConfigEntitiesByConfigNameContaining(configName);
  }

  @Override
  public void updateExamsPercentages(double academicWeight, double daiWeight,
      double englishWeight) {
    try {
      systemConfigRepository.updateExamsPercentages(academicWeight, daiWeight, englishWeight);
    } catch (Exception e) {
      throw new RuntimeException("Error al actualizar los porcentajes de los exámenes");
    }
  }

  @Override
  public List<SystemConfigEntity> getNotifications(String configName) {
    List<SystemConfigEntity> notifications =
        systemConfigRepository.getSystemConfigEntitiesByConfigNameContaining(configName);

    if (notifications.isEmpty()) {
      systemConfigRepository.updateNotifications("", "", "", "", "", "");
      notifications =
          systemConfigRepository.getSystemConfigEntitiesByConfigNameContaining(configName);
    }

    return notifications;
  }

  @Override
  public void updateNotifications(String emailContact, String emailNotificationsContact,
      String whatsappContact, String officeContact, String instagramContact,
      String facebookContact) {
    try {
      systemConfigRepository.updateNotifications(emailContact, emailNotificationsContact,
          whatsappContact, officeContact, instagramContact, facebookContact);
    } catch (Exception e) {
      throw new RuntimeException("Error al actualizar las notificaciones", e);
    }
  }
}
