package cr.co.ctpcit.citsacbackend.rest.configs;

import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.ExamPeriodDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.SystemConfigDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.UpdateContactInfoConfigsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.UpdateWeightsConfigsDto;
import cr.co.ctpcit.citsacbackend.logic.services.configs.SystemConfigServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/system-config")
@Validated
public class SystemConfigController {

  private final SystemConfigServiceImpl systemConfigService;

  @GetMapping("/get-process-weights")
  public ResponseEntity<List<SystemConfigDto>> getProcessWeights() {
    List<SystemConfigDto> processWeights = systemConfigService.getProcessWeights();

    return new ResponseEntity<>(processWeights, HttpStatus.OK);
  }

  @PutMapping("/update-process-weights")
  public ResponseEntity<String> updateProcessWeights(
      @RequestBody UpdateWeightsConfigsDto weightsConfigs) {

    systemConfigService.updateWeights(weightsConfigs.prevGradesWeight(),
        weightsConfigs.academicWeight(), weightsConfigs.englishWeight());

    // return the updated exams percentages
    return ResponseEntity.ok().build();
  }

  @GetMapping("/get-contact-info")
  public ResponseEntity<List<SystemConfigDto>> getContactInfo() {
    List<SystemConfigDto> contactInfo = systemConfigService.getContactInfo();

    return new ResponseEntity<>(contactInfo, HttpStatus.OK);
  }

  @PutMapping("/update-contact-info")
  public ResponseEntity<String> updateContactInfo(
      @RequestBody UpdateContactInfoConfigsDto contactInfoConfigsDto) {

    systemConfigService.updateContactInfo(contactInfoConfigsDto);

    return ResponseEntity.ok().build();
  }

  /**
   * Get the exam period by id
   *
   * @param id the id of the exam period
   * @return the exam period
   */
  @GetMapping("/get-exam-period/{id}")
  public ResponseEntity<ExamPeriodDto> getExamPeriod(@PathVariable Long id) {
    ExamPeriodDto examPeriod = systemConfigService.getExamPeriod(id);

    return new ResponseEntity<>(examPeriod, HttpStatus.OK);
  }

  /**
   * Get the exam periods for the current year
   *
   * @return a list of exam periods of the year
   */
  @GetMapping("/get-current-exam-periods")
  public ResponseEntity<List<ExamPeriodDto>> getCurrentExamPeriods() {
    List<ExamPeriodDto> examPeriods = systemConfigService.getCurrentExamPeriods();

    return new ResponseEntity<>(examPeriods, HttpStatus.OK);
  }

  /**
   * Get the exam periods for a given year
   *
   * @param year the year to get the exam periods
   * @return a list of exam periods of the year
   */
  @GetMapping("/get-exam-periods/{year}")
  public ResponseEntity<List<ExamPeriodDto>> getExamPeriods(@PathVariable Integer year) {
    List<ExamPeriodDto> examPeriods = systemConfigService.getExamPeriodsByYear(year);

    return new ResponseEntity<>(examPeriods, HttpStatus.OK);
  }

  /**
   * Create a new exam period
   *
   * @param examPeriodDto        the exam period to create
   * @param uriComponentsBuilder the uri components builder
   * @return the response entity
   */
  @PostMapping("/create-exam-period")
  public ResponseEntity<Void> createExamPeriod(@RequestBody ExamPeriodDto examPeriodDto,
      UriComponentsBuilder uriComponentsBuilder) {
    systemConfigService.createExamPeriod(examPeriodDto);

    return ResponseEntity.created(
        uriComponentsBuilder.path("/api/system-config/get-exam-period/{id}")
            .buildAndExpand(examPeriodDto.id()).toUri()).build();
  }


  @PutMapping("/academic-exam-questions-quantity/{quantity}")
  public ResponseEntity<String> updateAcademicExamQuestionsQuantity(
      @PathVariable Integer quantity) {
    systemConfigService.updateExamQuestionsQuantity(Configurations.ACADEMIC_EXAM_QUESTIONS_QUANTITY,
        quantity);

    return ResponseEntity.ok().build();
  }

  @PutMapping("/dai-exam-questions-quantity/{quantity}")
  public ResponseEntity<String> updateDaiExamQuestionsQuantity(@PathVariable Integer quantity) {
    systemConfigService.updateExamQuestionsQuantity(Configurations.DAI_EXAM_QUESTIONS_QUANTITY,
        quantity);

    return ResponseEntity.ok().build();
  }
}

