package cr.co.ctpcit.citsacbackend.data.repositories.config;

import cr.co.ctpcit.citsacbackend.data.entities.config.SystemConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfigEntity, Integer> {
  List<SystemConfigEntity> getSystemConfigEntitiesByConfigNameContaining(String configName);

  @Transactional
  @Modifying
  @Query(
      "UPDATE SystemConfigEntity s SET s.configValue = CASE s.configName " + "WHEN 'academic_weight' THEN ?1 " + "WHEN 'dai_weight' THEN ?2 " + "WHEN 'english_weight' THEN ?3 " + "END")
  void updateExamsPercentages(double academicWeight, double daiWeight, double englishWeight);

  @Transactional
  @Modifying
  @Query(
          "UPDATE SystemConfigEntity s SET s.configValue = CASE s.configName " + "WHEN 'email_contact' THEN ?1 " + "WHEN 'email_notifications_contact' THEN ?2 " + "WHEN 'whatsapp_contact' THEN ?3 " + "WHEN 'office_contact' THEN ?4 " + "WHEN 'instagram_contact' THEN ?5 " + "WHEN 'facebook_contact' THEN ?6 " + "END")
  void updateNotifications(String emailContact,String emailNotificationContact, String whatsappContact, String officeContact, String instagramContact, String facebookContact);
}
