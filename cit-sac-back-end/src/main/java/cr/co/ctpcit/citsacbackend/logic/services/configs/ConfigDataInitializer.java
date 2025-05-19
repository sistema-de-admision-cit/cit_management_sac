package cr.co.ctpcit.citsacbackend.logic.services.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import cr.co.ctpcit.citsacbackend.data.repositories.configs.SystemConfigRepository;
import cr.co.ctpcit.citsacbackend.data.utils.EncryptionUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class ConfigDataInitializer {
  private final SystemConfigRepository systemConfigRepository;
  private final EncryptionUtil encryptionUtil;

  public ConfigDataInitializer(SystemConfigRepository systemConfigRepository,
      EncryptionUtil encryptionUtil) {
    this.systemConfigRepository = systemConfigRepository;
    this.encryptionUtil = encryptionUtil;
  }

  public void initConfigs() {
    SystemConfigEntity prevGrades =
        SystemConfigEntity.builder().configName(Configurations.PREV_GRADES_WEIGHT)
            .configValue(String.valueOf(0.5)).build();
    SystemConfigEntity academic =
        SystemConfigEntity.builder().configName(Configurations.ACADEMIC_WEIGHT)
            .configValue(String.valueOf(0.5)).build();
    SystemConfigEntity english =
        SystemConfigEntity.builder().configName(Configurations.ENGLISH_WEIGHT)
            .configValue(String.valueOf(0)).build();
    SystemConfigEntity emailContact =
        SystemConfigEntity.builder().configName(Configurations.EMAIL_CONTACT)
            .configValue("servicioalcliente@ctpcit.co.cr").build();
    SystemConfigEntity emailNotif =
        SystemConfigEntity.builder().configName(Configurations.EMAIL_NOTIFICATION_CONTACT)
            .configValue("admision@ctpcit.co.cr").build();
    SystemConfigEntity emailPassword =
        SystemConfigEntity.builder().configName(Configurations.EMAIL_PASSWORD)
            .configValue(encryptionUtil.encrypt("qrcprxdzbpldvfdr")).build();
    SystemConfigEntity whatsapp =
        SystemConfigEntity.builder().configName(Configurations.WHATSAPP_CONTACT)
            .configValue("87149225").build();
    SystemConfigEntity officeContact =
        SystemConfigEntity.builder().configName(Configurations.OFFICE_CONTACT)
            .configValue("22390833").build();
    SystemConfigEntity instagramContact =
        SystemConfigEntity.builder().configName(Configurations.INSTAGRAM_CONTACT)
            .configValue("https://www.instagram.com/complejoeducativocit/").build();
    SystemConfigEntity facebookContact =
        SystemConfigEntity.builder().configName(Configurations.FACEBOOK_CONTACT)
            .configValue("https://www.facebook.com/share/1CDuSmqsKg/").build();
    SystemConfigEntity whatsappApiKey =
        SystemConfigEntity.builder().configName(Configurations.WHATSAPP_API_KEY)
            .configValue(encryptionUtil.encrypt("WhatsappApiKey")).build();
    SystemConfigEntity academicQuestion =
        SystemConfigEntity.builder().configName(Configurations.ACADEMIC_EXAM_QUESTIONS_QUANTITY)
            .configValue(String.valueOf(20)).build();
    SystemConfigEntity daiQuestion =
        SystemConfigEntity.builder().configName(Configurations.DAI_EXAM_QUESTIONS_QUANTITY)
            .configValue(String.valueOf(20)).build();

    saveIfNotExists(prevGrades);
    saveIfNotExists(academic);
    saveIfNotExists(english);
    saveIfNotExists(emailContact);
    saveIfNotExists(emailNotif);
    saveIfNotExists(emailPassword);
    saveIfNotExists(whatsapp);
    saveIfNotExists(officeContact);
    saveIfNotExists(instagramContact);
    saveIfNotExists(facebookContact);
    saveIfNotExists(whatsappApiKey);
    saveIfNotExists(academicQuestion);
    saveIfNotExists(daiQuestion);
  }

  private void saveIfNotExists(SystemConfigEntity entity) {
    if (!systemConfigRepository.existsByConfigName(entity.getConfigName())) {
      systemConfigRepository.save(entity);
    }
  }
}
