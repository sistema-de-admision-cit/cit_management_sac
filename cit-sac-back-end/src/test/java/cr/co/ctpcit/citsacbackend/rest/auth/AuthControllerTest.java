package cr.co.ctpcit.citsacbackend.rest.auth;

import cr.co.ctpcit.citsacbackend.logic.dto.auth.AuthResponseDto;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.ChangePasswordRequestDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Objects;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {
  @Autowired
  private TestRestTemplate restTemplate;

  private static String token;
  private static AuthResponseDto authResponseDto;

  @Test
  @Order(1)
  public void testLoginEndpoint() {
    HttpHeaders headers = new HttpHeaders();
    headers.setBasicAuth("rocio@cit.co.cr", "Mate8520");

    HttpEntity<String> entity = new HttpEntity<>(null, headers);
    ResponseEntity<AuthResponseDto> response =
        restTemplate.exchange("/api/auth/login", HttpMethod.POST, entity, AuthResponseDto.class);

    //Deserialize the response body
    authResponseDto = response.getBody();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(Objects.requireNonNull(response.getBody()));
    assertNotNull(response.getBody().token());
  }

  @Test
  @Order(2)
  public void testChangePasswordEndpoint() {
    ResponseEntity<String> response =
        restTemplate.exchange("/api/auth/change-password", HttpMethod.PUT,
            getChangePasswordRequest("Mate8520", "Sociales1650", "Sociales1650"), String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("Contraseña actualizada correctamente.", response.getBody());

    restTemplate.exchange("/api/auth/change-password", HttpMethod.PUT,
        getChangePasswordRequest("Sociales1650", "Mate8520", "Mate8520"), String.class);
  }

  private static HttpEntity<ChangePasswordRequestDTO> getChangePasswordRequest(
      String currentPassword, String newPassword, String confirmPassword) {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(authResponseDto.token());

    ChangePasswordRequestDTO request =
        new ChangePasswordRequestDTO(currentPassword, newPassword, confirmPassword);
    return new HttpEntity<>(request, headers);
  }
}
