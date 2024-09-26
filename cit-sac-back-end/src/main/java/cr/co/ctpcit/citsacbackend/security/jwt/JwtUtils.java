package cr.co.ctpcit.citsacbackend.security.jwt;

import cr.co.ctpcit.citsacbackend.data.entities.users.UserEntity;
import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

@Component
public class JwtUtils {
  private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

  @Value("${cit.app.jwtExpirationMs}")
  private int jwtExpirationMs;

  @Value("${cit.app.jwtCoockieName}")
  private String jwtCookie;

  private final PublicKey pubKey;
  private final PrivateKey privKey;

    public JwtUtils() {
        KeyPair keyPair = key();
        pubKey = keyPair.getPublic();
        privKey = keyPair.getPrivate();
    }

  public String getJwtFromCookies(HttpServletRequest request) {
    Cookie cookie = WebUtils.getCookie(request, jwtCookie);
    if (cookie != null) {
      return cookie.getValue();
    } else {
      return null;
    }
  }

  public ResponseCookie generateJwtCookie(UserEntity userPrincipal) {
    String jwt = generateTokenFromUsername(userPrincipal.getUsername(), userPrincipal.getRole().name());
    return ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(24 * 60 * 60).httpOnly(true).build();
  }

  public ResponseCookie getCleanJwtCookie() {
    return ResponseCookie.from(jwtCookie).path("/api").build();
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().verifyWith(pubKey).build().parseSignedClaims(token).getPayload().getSubject();
  }

  private KeyPair key() {
    return Jwts.SIG.RS256.keyPair().build();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().verifyWith(pubKey).build().parseSignedClaims(authToken);
      return true;
    } catch (JwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      logger.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }

  public String generateTokenFromUsername(String username, String role) {
    return Jwts.builder()
        .subject(username)
        .issuedAt(new Date())
        .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .claim("role", role)
        .signWith(privKey)
        .compact();
  }
}
