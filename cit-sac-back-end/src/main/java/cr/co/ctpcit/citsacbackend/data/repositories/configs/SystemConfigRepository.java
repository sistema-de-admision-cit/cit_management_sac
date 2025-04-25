package cr.co.ctpcit.citsacbackend.data.repositories.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SystemConfigRepository extends JpaRepository<SystemConfigEntity, Integer> {
  Optional<SystemConfigEntity> findByConfigName(Configurations configName);

  @Query(
      "SELECT s FROM SystemConfigEntity s WHERE s.configName = 'ACADEMIC_WEIGHT' OR s.configName = 'PREV_GRADES_WEIGHT' OR s.configName = 'ENGLISH_WEIGHT'")
  List<SystemConfigEntity> getProcessWeights();

  @Query(
      "SELECT s FROM SystemConfigEntity s WHERE s.configName = 'EMAIL_CONTACT' OR s.configName = 'EMAIL_NOTIFICATION_CONTACT' OR s.configName = 'WHATSAPP_CONTACT' OR s.configName = 'OFFICE_CONTACT' OR s.configName = 'INSTAGRAM_CONTACT' OR s.configName = 'FACEBOOK_CONTACT' OR s.configName = 'EMAIL_PASSWORD' OR s.configName = 'WHATSAPP_API_KEY'")
  List<SystemConfigEntity> getContactInfo();

  @Modifying
  @Transactional
  @Query(
      "UPDATE SystemConfigEntity s SET s.configValue = :configValue WHERE s.configName = :configName")
  void updateByConfigName(Configurations configName, String configValue);
}
