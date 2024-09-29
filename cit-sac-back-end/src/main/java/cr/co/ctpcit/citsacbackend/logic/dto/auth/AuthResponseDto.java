package cr.co.ctpcit.citsacbackend.logic.dto.auth;

import lombok.Builder;

@Builder
public record AuthResponseDto(String type, String token, Boolean isDefaultPassword) {
  public AuthResponseDto(String token, Boolean isDefaultPassword) {
    this("Bearer", token, isDefaultPassword);
  }
}
