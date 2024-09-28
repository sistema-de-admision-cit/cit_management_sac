package cr.co.ctpcit.citsacbackend.data.repositories.config;

import cr.co.ctpcit.citsacbackend.data.entities.config.SystemConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
  @Query("UPDATE SystemConfigEntity s SET s.configValue = CASE s.configName " +
          "WHEN 'email_contact' THEN :emailContact " +
          "WHEN 'email_notifications_contact' THEN :emailNotificationContact " +
          "WHEN 'whatsapp_contact' THEN :whatsappContact " +
          "WHEN 'office_contact' THEN :officeContact " +
          "WHEN 'instagram_contact' THEN :instagramContact " +
          "WHEN 'facebook_contact' THEN :facebookContact " +
          "END WHERE s.configName IN ('email_contact', 'email_notifications_contact', 'whatsapp_contact', 'office_contact', 'instagram_contact', 'facebook_contact')")
  void updateNotifications(@Param("emailContact") String emailContact,
                           @Param("emailNotificationContact") String emailNotificationContact,
                           @Param("whatsappContact") String whatsappContact,
                           @Param("officeContact") String officeContact,
                           @Param("instagramContact") String instagramContact,
                           @Param("facebookContact") String facebookContact);

}
