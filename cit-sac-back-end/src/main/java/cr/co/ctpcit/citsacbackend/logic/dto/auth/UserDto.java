package cr.co.ctpcit.citsacbackend.logic.dto.auth;

import cr.co.ctpcit.citsacbackend.data.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.users.UserEntity}
 */
@Builder
public record UserDto(Long id, @NotNull @Size(max = 128) @NotBlank(
    message = "El correo electrónico no puede estar vacío") String email,
                      @NotNull @Size(min = 8, max = 128) @Pattern(
                          message = "La contraseña debe tener al menos 8 caracteres, una letra y un número",
                          regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$") @NotBlank(
                          message = "La contraseña no puede estar vacía") String userPassword,
                      Role role) implements Serializable {
}
