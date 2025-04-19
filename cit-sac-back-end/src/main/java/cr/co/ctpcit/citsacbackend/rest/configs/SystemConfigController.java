package cr.co.ctpcit.citsacbackend.rest.configs;

import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.*;
import cr.co.ctpcit.citsacbackend.logic.services.configs.SystemConfigServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
/**
 * REST controller for managing system configurations.
 * Provides endpoints for retrieving and updating various system configuration parameters.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/system-config")
@Validated
public class SystemConfigController {

  private final SystemConfigServiceImpl systemConfigService;
  /**
   * Retrieves the quantity of questions configured in the system.
   *
   * @return ResponseEntity containing a list of SystemConfigDto with question quantities
   */
  @GetMapping("/get-questions-quantity")
  public ResponseEntity<List<SystemConfigDto>> getQuestionsQuantity() {
    List<SystemConfigDto> questionsQuantity = systemConfigService.getQuestionsQuantity();
    return new ResponseEntity<>(questionsQuantity, HttpStatus.OK);
  }
  /**
   * Updates the quantity of questions in the system.
   *
   * @param questionsConfigs DTO containing the new question quantities
   * @return ResponseEntity with OK status
   */
  @PutMapping("/update-questions-quantity")
  public ResponseEntity<String> updateQuestionsQuantity(
          @RequestBody UpdateQuantityConfigsDto questionsConfigs) {
    systemConfigService.updateQuantity(questionsConfigs.daiQuestionsQuantity(),questionsConfigs.academicQuestionsQuantity());
    // return the updated questions quantity
    return ResponseEntity.ok().build();
  }
  /**
   * Retrieves the process weights configured in the system.
   *
   * @return ResponseEntity containing a list of SystemConfigDto with process weights
   */
  @GetMapping("/get-process-weights")
  public ResponseEntity<List<SystemConfigDto>> getProcessWeights() {
    List<SystemConfigDto> processWeights = systemConfigService.getProcessWeights();

    return new ResponseEntity<>(processWeights, HttpStatus.OK);
  }
  /**
   * Updates the process weights in the system.
   *
   * @param weightsConfigs DTO containing the new process weights
   * @return ResponseEntity with OK status
   */
  @PutMapping("/update-process-weights")
  public ResponseEntity<String> updateProcessWeights(
      @RequestBody UpdateWeightsConfigsDto weightsConfigs) {

    systemConfigService.updateWeights(weightsConfigs.prevGradesWeight(),
        weightsConfigs.academicWeight(), weightsConfigs.englishWeight());

    // return the updated exams percentages
    return ResponseEntity.ok().build();
  }
  /**
   * Retrieves the contact information configured in the system.
   *
   * @return ResponseEntity containing a list of SystemConfigDto with contact information
   */
  @GetMapping("/get-contact-info")
  public ResponseEntity<List<SystemConfigDto>> getContactInfo() {
    List<SystemConfigDto> contactInfo = systemConfigService.getContactInfo();

    return new ResponseEntity<>(contactInfo, HttpStatus.OK);
  }
  /**
   * Updates the contact information in the system.
   *
   * @param contactInfoConfigsDto DTO containing the new contact information
   * @return ResponseEntity with OK status
   */
  @PutMapping("/update-contact-info")
  public ResponseEntity<String> updateContactInfo(
      @RequestBody UpdateContactInfoConfigsDto contactInfoConfigsDto) {

    systemConfigService.updateContactInfo(contactInfoConfigsDto);

    return ResponseEntity.ok().build();
  }
  /**
   * Retrieves a specific exam period by its ID.
   *
   * @param id the ID of the exam period to retrieve
   * @return ResponseEntity containing the ExamPeriodDto
   */
  @GetMapping("/get-exam-period/{id}")
  public ResponseEntity<ExamPeriodDto> getExamPeriod(@PathVariable Long id) {
    ExamPeriodDto examPeriod = systemConfigService.getExamPeriod(id);

    return new ResponseEntity<>(examPeriod, HttpStatus.OK);
  }
  /**
   * Retrieves all exam periods for the current year.
   *
   * @return ResponseEntity containing a list of ExamPeriodDto
   */
  @GetMapping("/get-current-exam-periods")
  public ResponseEntity<List<ExamPeriodDto>> getCurrentExamPeriods() {
    List<ExamPeriodDto> examPeriods = systemConfigService.getCurrentExamPeriods();

    return new ResponseEntity<>(examPeriods, HttpStatus.OK);
  }
  /**
   * Retrieves all exam periods for a specific year.
   *
   * @param year the year for which to retrieve exam periods
   * @return ResponseEntity containing a list of ExamPeriodDto
   */
  @GetMapping("/get-exam-periods/{year}")
  public ResponseEntity<List<ExamPeriodDto>> getExamPeriods(@PathVariable Integer year) {
    List<ExamPeriodDto> examPeriods = systemConfigService.getExamPeriodsByYear(year);

    return new ResponseEntity<>(examPeriods, HttpStatus.OK);
  }
  /**
   * Creates a new exam period.
   *
   * @param examPeriodDto DTO containing the exam period details to create
   * @param uriComponentsBuilder builder for constructing the response URI
   * @return ResponseEntity with created status and location header
   */
  @PostMapping("/create-exam-period")
  public ResponseEntity<Void> createExamPeriod(@RequestBody ExamPeriodDto examPeriodDto,
      UriComponentsBuilder uriComponentsBuilder) {
    systemConfigService.createExamPeriod(examPeriodDto);

    return ResponseEntity.created(
        uriComponentsBuilder.path("/api/system-config/get-exam-period/{id}")
            .buildAndExpand(examPeriodDto.id()).toUri()).build();
  }
  /**
   * Updates the quantity of questions for academic exams.
   *
   * @param quantity the new quantity of questions
   * @return ResponseEntity with OK status
   */
  @PutMapping("/academic-exam-questions-quantity/{quantity}")
  public ResponseEntity<String> updateAcademicExamQuestionsQuantity(
      @PathVariable Integer quantity) {
    systemConfigService.updateExamQuestionsQuantity(Configurations.ACADEMIC_EXAM_QUESTIONS_QUANTITY,
        quantity);

    return ResponseEntity.ok().build();
  }
  /**
   * Updates the quantity of questions for DAI exams.
   *
   * @param quantity the new quantity of questions
   * @return ResponseEntity with OK status
   */
  @PutMapping("/dai-exam-questions-quantity/{quantity}")
  public ResponseEntity<String> updateDaiExamQuestionsQuantity(@PathVariable Integer quantity) {
    systemConfigService.updateExamQuestionsQuantity(Configurations.DAI_EXAM_QUESTIONS_QUANTITY,
        quantity);

    return ResponseEntity.ok().build();
  }
}

