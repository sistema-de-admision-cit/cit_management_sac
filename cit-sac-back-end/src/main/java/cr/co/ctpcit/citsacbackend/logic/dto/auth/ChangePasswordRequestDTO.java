package cr.co.ctpcit.citsacbackend.logic.dto.auth;

import jakarta.validation.constraints.Pattern;

public record ChangePasswordRequestDTO(String currentPassword, @Pattern(
    regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$") String newPassword,
                                       String confirmPassword) {
}
