package cr.co.ctpcit.citsacbackend.rest.auth;

import cr.co.ctpcit.citsacbackend.logic.dto.auth.AuthResponseDto;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.ChangePasswordRequestDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.UserDto;
import cr.co.ctpcit.citsacbackend.logic.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final UserService userService;
  private final JwtEncoder encoder;

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDto> token(Authentication authentication) {
    Instant now = Instant.now();
    long expiry = 36000L;

    UserDto userDetails = (UserDto) authentication.getPrincipal();
    // @formatter:off
    String scope = authentication.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(" "));
    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(now.plusSeconds(expiry))
        .subject(authentication.getName())
        .claim("scope", scope)
        .build();
    // @formatter:on

    return ResponseEntity.ok(AuthResponseDto.builder().type("Bearer")
        .token(this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue())
        .isDefaultPassword(userDetails.getIsDefaultPassword()).build());
  }

  @PutMapping("/change-password")
  public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequestDTO request,
      Authentication authentication) {
    String userEmail = authentication.getName();

    // Lógica para cambiar la contraseña
    try {
      this.userService.updatePassword(userEmail, request);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    return ResponseEntity.ok("Contraseña actualizada correctamente.");
  }
}
