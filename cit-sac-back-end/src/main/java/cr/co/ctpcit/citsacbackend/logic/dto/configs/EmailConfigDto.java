package cr.co.ctpcit.citsacbackend.logic.dto.configs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class EmailConfigDto {

    @NotBlank(message = "El destinatario no puede estar vacío")
    @Email(message = "Debe ser un correo electrónico válido")
    private String recipient;

    @NotBlank(message = "El asunto no puede estar vacío")
    @Size(max = 100, message = "El asunto no debe exceder los 100 caracteres")
    private String subject;

    @NotBlank(message = "El mensaje no puede estar vacío")
    @Size(max = 5000, message = "El mensaje no debe exceder los 5000 caracteres")
    private String message;
}