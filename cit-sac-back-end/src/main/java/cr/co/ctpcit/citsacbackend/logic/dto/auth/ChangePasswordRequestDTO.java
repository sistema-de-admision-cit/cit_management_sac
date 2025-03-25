package cr.co.ctpcit.citsacbackend.logic.dto.auth;

import jakarta.validation.constraints.Pattern;

public record ChangePasswordRequestDTO(String currentPassword, @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])[A-Za-z\\d!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]{8,}$",
        message = "La contraseña debe tener mínimo 8 caracteres, al menos una letra, un número y un carácter especial.") String newPassword,
                                       String confirmPassword) {
}
