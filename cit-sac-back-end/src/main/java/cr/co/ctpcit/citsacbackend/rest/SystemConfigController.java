package cr.co.ctpcit.citsacbackend.rest;

import cr.co.ctpcit.citsacbackend.logic.dto.SystemConfigDto;
import cr.co.ctpcit.citsacbackend.logic.services.SystemConfigServiceImplementation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}

