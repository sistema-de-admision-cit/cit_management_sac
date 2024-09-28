package cr.co.ctpcit.citsacbackend.rest.config;

import cr.co.ctpcit.citsacbackend.data.entities.config.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.config.SystemConfigDto;
import cr.co.ctpcit.citsacbackend.logic.services.SystemConfigServiceImplementation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/system-config")
public class SystemConfigController {

  private final SystemConfigServiceImplementation systemConfigService;

  @PreAuthorize("hasAuthority('SCOPE_S')")
  @PostMapping
  public ResponseEntity<SystemConfigDto> addSystemConfig(
      @RequestBody @Valid SystemConfigDto systemConfigDto) {
    SystemConfigDto savedConfig = systemConfigService.addSystemConfig(systemConfigDto);
    return new ResponseEntity<>(savedConfig, HttpStatus.CREATED);
  }

  @PreAuthorize("hasAuthority('SCOPE_S')")
  @GetMapping("/get-exams-percentages")
  public ResponseEntity<List<SystemConfigEntity>> getExamsPercentages() {
    List<SystemConfigEntity> examsPercentages = systemConfigService.getExamsPercentages("weight");
    return new ResponseEntity<>(examsPercentages, HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('SCOPE_S')")
  @PutMapping("/update-exams-percentages")
  public ResponseEntity<List<SystemConfigEntity>> updateExamsPercentages(
      @RequestParam("academic_weight") double academicWeight,
      @RequestParam("dai_weight") double daiWeight,
      @RequestParam("english_weight") double englishWeight) {

    systemConfigService.updateExamsPercentages(academicWeight, daiWeight, englishWeight);

    // return the updated exams percentages
    return new ResponseEntity<>(getExamsPercentages().getBody(), HttpStatus.OK);
  }

  @GetMapping("/get-notifications")
  public ResponseEntity<List<SystemConfigEntity>> getNotifications() {
    List<SystemConfigEntity> notifications = systemConfigService.getNotifications("contact");
    return new ResponseEntity<>(notifications, HttpStatus.OK);
  }

  @PutMapping("/update-notifications")
  public ResponseEntity<List<SystemConfigEntity>> updateNotifications(
          @RequestParam("email_contact") String emailContact,
          @RequestParam("email_notifications_contact") String emailNotificationsContact,
          @RequestParam("whatsapp_contact") String whatsappContact,
          @RequestParam("office_contact") String officeContact,
          @RequestParam("instagram_contact") String instagramContact,
          @RequestParam("facebook_contact") String facebookContact) {

    systemConfigService.updateNotifications(emailContact,emailNotificationsContact,whatsappContact,officeContact,instagramContact,facebookContact);

    return new ResponseEntity<>(getNotifications().getBody(), HttpStatus.OK);
  }



}

