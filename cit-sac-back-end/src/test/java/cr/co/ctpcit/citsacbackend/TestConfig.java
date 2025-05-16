package cr.co.ctpcit.citsacbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;

@TestConfiguration
public class TestConfig {
  @Autowired
  private JwtEncoder jwtEncoder;

  @Bean
  public String generateTestToken() {
    Instant now = Instant.now();
    long expiry = 3600L; // 1 hora

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("self")
        .issuedAt(now)
        .expiresAt(now.plusSeconds(expiry))
        .subject("test-user")
        .claim("scope", "SCOPE_ADMIN") // personaliza según tu config
        .build();

    return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }

  @Bean
  public HttpHeaders getBearerHeader() {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(generateTestToken());
    return headers;
  }

  @Bean
  public HttpEntity<String> getHttpEntity() {
    return new HttpEntity<>(null, getBearerHeader());
  }
}
