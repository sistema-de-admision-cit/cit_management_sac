package cr.co.ctpcit.citsacbackend.logic.dto.auth;

import lombok.Builder;

/**
 * Represents the authentication response data transfer object (DTO).
 * Contains information about the authentication type, token, and whether the default password is used.
 */
@Builder
public record AuthResponseDto(String type, String token, Boolean isDefaultPassword) {
  /**
   * Constructs an AuthResponseDto with a default type of "Bearer".
   *
   * @param token The authentication token.
   * @param isDefaultPassword Indicates whether the user is using a default password.
   */
  public AuthResponseDto(String token, Boolean isDefaultPassword) {
    this("Bearer", token, isDefaultPassword);
  }
}
