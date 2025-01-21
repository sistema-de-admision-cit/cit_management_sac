package cr.co.ctpcit.citsacbackend.logic.dto.config;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailConfigDto {
    @NotBlank
    @Email
    private String recipient;

    @NotBlank
    private String subject;

    @NotBlank
    private String message;
}
