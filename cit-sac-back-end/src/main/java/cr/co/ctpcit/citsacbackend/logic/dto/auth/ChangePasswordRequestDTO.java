package cr.co.ctpcit.citsacbackend.logic.dto.auth;

import jakarta.validation.constraints.Pattern;

/**
 * Represents the data transfer object (DTO) for changing a user's password.
 * Contains the current password, the new password with validation constraints, and the password confirmation.
 */
public record ChangePasswordRequestDTO(String currentPassword,
                                       /**
                                        * The new password that the user wants to set. The password must meet the following criteria:
                                        * - At least 8 characters long.
                                        * - Contains at least one letter, one number, and one special character.
                                        */
                                       @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])[A-Za-z\\d!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]{8,}$",
        message = "La contraseña debe tener mínimo 8 caracteres, al menos una letra, un número y un carácter especial.") String newPassword,
                                       String confirmPassword) {
}
