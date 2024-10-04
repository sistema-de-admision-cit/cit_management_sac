package cr.co.ctpcit.citsacbackend.logic.dto.auth;

import jakarta.validation.constraints.Pattern;

public record ChangePasswordRequestDTO(String currentPassword, @Pattern(
    regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
    message = "La contraseña debe tener minimo 8 caracteres, letras y numeros.") String newPassword,
                                       String confirmPassword) {
}
