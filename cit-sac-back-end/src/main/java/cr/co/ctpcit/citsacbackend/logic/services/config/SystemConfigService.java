package cr.co.ctpcit.citsacbackend.logic.services.config;

import cr.co.ctpcit.citsacbackend.data.entities.config.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.config.SystemConfigDto;

import java.util.List;

public interface SystemConfigService {
  SystemConfigDto addSystemConfig(SystemConfigDto systemConfigDto);

  List<SystemConfigEntity> getExamsPercentages(String configName);

  void updateExamsPercentages(double academicWeight, double daiWeight, double englishWeight);

  List<SystemConfigEntity> getNotifications(String configName);

  void updateNotifications(String emailContact, String emailNotificationsContact,
      String whatsappContact, String officeContact, String instagramContact,
      String facebookContact);
}
