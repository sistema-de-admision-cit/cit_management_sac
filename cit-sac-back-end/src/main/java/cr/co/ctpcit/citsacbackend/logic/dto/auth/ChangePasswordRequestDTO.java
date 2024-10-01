package cr.co.ctpcit.citsacbackend.logic.dto.auth;

public record ChangePasswordRequestDTO(String currentPassword, String newPassword,
                                       String confirmPassword) {
}
