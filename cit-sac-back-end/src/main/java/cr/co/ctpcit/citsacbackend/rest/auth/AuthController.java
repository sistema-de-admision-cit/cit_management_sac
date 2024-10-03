package cr.co.ctpcit.citsacbackend.rest.auth;

import cr.co.ctpcit.citsacbackend.logic.dto.auth.AuthResponseDto;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.ChangePasswordRequestDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.UserDto;
import cr.co.ctpcit.citsacbackend.security.DaoAuthenticationProviderCstm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final JwtEncoder encoder;
  private final DaoAuthenticationProviderCstm daoAuthenticationProvider;

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
  public ResponseEntity<String> changePassword(
      @RequestBody @Valid ChangePasswordRequestDTO request) {
    // Lógica para cambiar la contraseña
    daoAuthenticationProvider.updatePassword(request);
    return ResponseEntity.ok("Contraseña actualizada correctamente.");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ResponseEntity<String> handleConstraintViolationException(MethodArgumentNotValidException e) {
    return new ResponseEntity<>(
        "La contraseña debe tener al menos 8 caracteres y contener tanto letras como números.",
        HttpStatus.BAD_REQUEST);
  }
}
