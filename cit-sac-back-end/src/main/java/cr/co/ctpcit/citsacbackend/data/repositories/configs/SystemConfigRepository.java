package cr.co.ctpcit.citsacbackend.data.repositories.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link SystemConfigEntity} entities.
 * Provides custom query methods for retrieving specific system configuration settings.
 */
public interface SystemConfigRepository extends JpaRepository<SystemConfigEntity, Integer> {

  /**
   * Finds a system configuration by its configuration name.
   *
   * @param configName the name of the configuration
   * @return an {@link Optional} containing the matching configuration, if found
   */
  Optional<SystemConfigEntity> findByConfigName(Configurations configName);

  /**
   * Retrieves configuration entries related to the number of questions for DAI and Academic exams.
   *
   * @return a list of {@link SystemConfigEntity} entries for exam question quantities
   */
  @Query(
          "SELECT s FROM SystemConfigEntity s WHERE s.configName = 'DAI_EXAM_QUESTIONS_QUANTITY' OR s.configName = 'ACADEMIC_EXAM_QUESTIONS_QUANTITY'")
  List<SystemConfigEntity> getQuestionsQuantity();

  /**
   * Retrieves configuration entries related to weights used in the student evaluation process.
   * These include academic, previous grades, and English weights.
   *
   * @return a list of {@link SystemConfigEntity} entries for process weight configurations
   */
  @Query(
      "SELECT s FROM SystemConfigEntity s WHERE s.configName = 'ACADEMIC_WEIGHT' OR s.configName = 'PREV_GRADES_WEIGHT' OR s.configName = 'ENGLISH_WEIGHT'")
  List<SystemConfigEntity> getProcessWeights();

  /**
   * Retrieves system configuration entries for contact information such as email, WhatsApp,
   * office phone, and social media handles.
   *
   * @return a list of {@link SystemConfigEntity} entries for contact information
   */
  @Query(
      "SELECT s FROM SystemConfigEntity s WHERE s.configName = 'EMAIL_CONTACT' OR s.configName = 'EMAIL_NOTIFICATION_CONTACT' OR s.configName = 'WHATSAPP_CONTACT' OR s.configName = 'OFFICE_CONTACT' OR s.configName = 'INSTAGRAM_CONTACT' OR s.configName = 'FACEBOOK_CONTACT'")
  List<SystemConfigEntity> getContactInfo();
}
