package cr.co.ctpcit.citsacbackend.logic.services.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.SystemConfigDto;

import java.util.List;

public interface SystemConfigService {
  SystemConfigDto addSystemConfig(SystemConfigDto systemConfigDto);

  List<SystemConfigDto> getProcessWeights();

  void updateWeights(Double prevGradesWeight, Double academicWeight, Double englishWeight);

  List<SystemConfigEntity> getNotifications(Configurations configName);

  void updateNotifications(String emailContact, String emailNotificationsContact,
      String whatsappContact, String officeContact, String instagramContact,
      String facebookContact);
}
