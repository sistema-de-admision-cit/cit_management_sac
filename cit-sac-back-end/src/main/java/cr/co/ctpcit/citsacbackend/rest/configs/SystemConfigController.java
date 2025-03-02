package cr.co.ctpcit.citsacbackend.rest.configs;

import cr.co.ctpcit.citsacbackend.logic.dto.configs.ExamPeriodDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.SystemConfigDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.UpdateContactInfoConfigsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.UpdateWeightsConfigsDto;
import cr.co.ctpcit.citsacbackend.logic.services.configs.SystemConfigServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
   * Get the exam periods for the current year
   * @return a list of exam periods of the year
   */
  @GetMapping("/get-current-exam-periods")
    public ResponseEntity<List<ExamPeriodDto>> getCurrentExamPeriods() {
        List<ExamPeriodDto> examPeriods = systemConfigService.getCurrentExamPeriods();

        return new ResponseEntity<>(examPeriods, HttpStatus.OK);
    }
}

