package cr.co.ctpcit.citsacbackend.logic.services.configs;

import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.ExamPeriodDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.SystemConfigDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.UpdateContactInfoConfigsDto;

import java.util.List;
/**
 * Service interface for managing system configurations.
 * Provides methods for handling various system settings including:
 * - Question quantities for exams
 * - Process weights for evaluations
 * - Contact information
 * - Exam periods management
 */
public interface SystemConfigService {
  /**
   * Retrieves the configured quantities of questions for different exam types.
   *
   * @return List of SystemConfigDto containing question quantities
   */
  List<SystemConfigDto> getQuestionsQuantity();

  /**
   * Updates the question quantities for different exam types.
   *
   * @param daiQuantity the new number of questions for DAI exams
   * @param academicQuantity the new number of questions for academic exams
   * @throws org.springframework.web.server.ResponseStatusException if any of the quantities is negative
   */
  void updateQuantity(int daiQuantity, int academicQuantity);

  /**
   * Retrieves the configured process weights for different evaluation components.
   *
   * @return a list of system configuration DTOs containing weights for previous grades, academic, and English components
   */
  List<SystemConfigDto> getProcessWeights();

  /**
   * Updates the process weights for different evaluation components.
   * The sum of all weights must equal 1.
   *
   * @param prevGradesWeight the weight for previous grades component
   * @param academicWeight the weight for academic component

   * @throws org.springframework.web.server.ResponseStatusException if the sum of weights is not equal to 1
   */
  void updateWeights(Double prevGradesWeight, Double academicWeight);
  /**
   * Retrieves the configured contact information.
   *
   * @return List of SystemConfigDto containing contact information
   */
  List<SystemConfigDto> getContactInfo();

  /**
   * Updates the contact information with the provided values.
   * Some values are stored as sensitive information and will be encrypted.
   *
   * @param contactInfoConfigsDto DTO containing the new contact information values
   */
  void updateContactInfo(UpdateContactInfoConfigsDto contactInfoConfigsDto);

  /**
   * Retrieves an exam period by its ID.
   *
   * @param id the ID of the exam period to retrieve
   * @return the exam period DTO
   * @throws org.springframework.web.server.ResponseStatusException if the exam period is not found
   */
  ExamPeriodDto getExamPeriod(Long id);
  /**
   * Retrieves all exam periods for the current academic year.
   *
   * @return List of ExamPeriodDto for current year
   */
  List<ExamPeriodDto> getCurrentExamPeriods();
  /**
   * Retrieves all exam periods for a specific academic year.
   *
   * @param year the academic year to filter exam periods
   * @return List of ExamPeriodDto for specified year
   */
  List<ExamPeriodDto> getExamPeriodsByYear(int year);

  /**
   * Creates a new exam period with its associated exam days.
   * Validates that the exam period doesn't already exist and doesn't overlap with existing periods.
   *
   * @param examPeriodDto the exam period DTO containing the period details and exam days
   * @throws org.springframework.web.server.ResponseStatusException if the exam period already exists or overlaps with another period
   */
  void createExamPeriod(ExamPeriodDto examPeriodDto);

  /**
   * Updates the number of questions for a specific exam type.
   *
   * @param config the configuration to update (must be ACADEMIC_EXAM_QUESTIONS_QUANTITY or DAI_EXAM_QUESTIONS_QUANTITY)
   * @param quantity the new number of questions
   * @throws org.springframework.web.server.ResponseStatusException if the configuration is not valid for exam questions quantity
   */
  void updateExamQuestionsQuantity(Configurations config, Integer quantity);

  /**
   * Retrieves the value of a configuration.
   * If the configuration is marked as sensitive, the value will be decrypted before returning.
   *
   * @param configName the name of the configuration to retrieve
   * @param isSensible whether the configuration value is sensitive and needs decryption
   * @return the configuration value (decrypted if sensitive)
   */
  String getConfigValue(Configurations configName, boolean isSensible);

  /**
   * Deletes an exam period by its ID.
   *
   * @param id the ID of the exam period to delete
   */
  void deleteExamPeriod(Long id);
}
