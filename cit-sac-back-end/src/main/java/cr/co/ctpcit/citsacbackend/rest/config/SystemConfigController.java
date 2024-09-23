package cr.co.ctpcit.citsacbackend.rest.config;

import cr.co.ctpcit.citsacbackend.data.entities.config.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.SystemConfigDto;
import cr.co.ctpcit.citsacbackend.logic.services.SystemConfigServiceImplementation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/system-config")
public class SystemConfigController {

  private final SystemConfigServiceImplementation systemConfigService;

  @PostMapping
  public ResponseEntity<SystemConfigDto> addSystemConfig(
      @RequestBody @Valid SystemConfigDto systemConfigDto) {
    SystemConfigDto savedConfig = systemConfigService.addSystemConfig(systemConfigDto);
    return new ResponseEntity<>(savedConfig, HttpStatus.CREATED);
  }

  @GetMapping("/get-exams-percentages")
  public ResponseEntity<List<SystemConfigEntity>> getExamsPercentages() {
    List<SystemConfigEntity> examsPercentages = systemConfigService.getExamsPercentages("weight");
    return new ResponseEntity<>(examsPercentages, HttpStatus.OK);
  }

  @PutMapping("/update-exams-percentages")
  public ResponseEntity<List<SystemConfigEntity>> updateExamsPercentages(
      @RequestParam("academic_weight") double academicWeight,
      @RequestParam("dai_weight") double daiWeight,
      @RequestParam("english_weight") double englishWeight) {

    systemConfigService.updateExamsPercentages(academicWeight, daiWeight, englishWeight);

    // return the updated exams percentages
    return new ResponseEntity<>(getExamsPercentages().getBody(), HttpStatus.OK);
  }
}

