package cr.co.ctpcit.citsacbackend.rest.configs;

import cr.co.ctpcit.citsacbackend.logic.dto.configs.SystemConfigDto;
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

  @PostMapping
  public ResponseEntity<SystemConfigDto> addSystemConfig(
      @RequestBody @Valid SystemConfigDto systemConfigDto) {
    SystemConfigDto savedConfig = systemConfigService.addSystemConfig(systemConfigDto);

    return new ResponseEntity<>(savedConfig, HttpStatus.CREATED);
  }

  @GetMapping("/get-process-weights")
  public ResponseEntity<List<SystemConfigDto>> getProcessWeights() {
    List<SystemConfigDto> processWeights = systemConfigService.getProcessWeights();

    return new ResponseEntity<>(processWeights, HttpStatus.OK);
  }

  @PutMapping("/update-process-weights")
  public ResponseEntity<String> updateExamsPercentages(
      @RequestBody UpdateWeightsConfigsDto weightsConfigs) {

    systemConfigService.updateWeights(weightsConfigs.prevGradesWeight(),
        weightsConfigs.academicWeight(), weightsConfigs.englishWeight());

    // return the updated exams percentages
    return ResponseEntity.ok().build();
  }

  /*@GetMapping("/get-notifications")
  public ResponseEntity<List<SystemConfigDto>> getNotifications() {

    List<SystemConfigDto> notifications = systemConfigService.getNotifications();
    return new ResponseEntity<>(notifications, HttpStatus.OK);
  }*/

  /*@PutMapping("/update-notifications")
  public ResponseEntity<List<SystemConfigEntity>> updateNotifications(
      @RequestParam("email_contact") String emailContact,
      @RequestParam("email_notifications_contact") String emailNotificationsContact,
      @RequestParam("whatsapp_contact") String whatsappContact,
      @RequestParam("office_contact") String officeContact,
      @RequestParam("instagram_contact") String instagramContact,
      @RequestParam("facebook_contact") String facebookContact) {

    systemConfigService.updateNotifications(emailContact, emailNotificationsContact,
        whatsappContact, officeContact, instagramContact, facebookContact);

    return new ResponseEntity<>(getNotifications().getBody(), HttpStatus.OK);
  }*/


}

