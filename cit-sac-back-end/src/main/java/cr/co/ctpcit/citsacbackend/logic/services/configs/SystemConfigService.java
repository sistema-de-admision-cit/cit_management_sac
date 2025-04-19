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
   * Updates the quantities of questions for different exam types.
   *
   * @param daiQuantity the new quantity of questions for DAI exams
   * @param academicQuantity the new quantity of questions for academic exams
   */
  void updateQuantity(int daiQuantity, int academicQuantity);
  /**
   * Retrieves the configured weights for different evaluation processes.
   *
   * @return List of SystemConfigDto containing process weights
   */
  List<SystemConfigDto> getProcessWeights();
  /**
   * Updates the weights for different evaluation components.
   *
   * @param prevGradesWeight weight for previous grades evaluation
   * @param academicWeight weight for academic exam evaluation
   * @param englishWeight weight for English exam evaluation
   */
  void updateWeights(Double prevGradesWeight, Double academicWeight, Double englishWeight);
  /**
   * Retrieves the configured contact information.
   *
   * @return List of SystemConfigDto containing contact information
   */
  List<SystemConfigDto> getContactInfo();
  /**
   * Updates the system's contact information.
   *
   * @param contactInfoConfigsDto DTO containing new contact information
   */
  void updateContactInfo(UpdateContactInfoConfigsDto contactInfoConfigsDto);
  /**
   * Retrieves a specific exam period by its ID.
   *
   * @param id the ID of the exam period to retrieve
   * @return ExamPeriodDto containing exam period details
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
   * Creates a new exam period configuration.
   *
   * @param examPeriodDto DTO containing exam period details to create
   */
  void createExamPeriod(ExamPeriodDto examPeriodDto);
  /**
   * Updates the question quantity for a specific exam configuration.
   *
   * @param config the exam configuration type to update
   * @param quantity the new quantity of questions to set
   */
  void updateExamQuestionsQuantity(Configurations config, Integer quantity);
}
