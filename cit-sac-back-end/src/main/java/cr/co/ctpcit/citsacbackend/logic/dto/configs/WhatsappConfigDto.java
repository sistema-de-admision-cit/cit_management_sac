package cr.co.ctpcit.citsacbackend.logic.dto.configs;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class WhatsappConfigDto {

    @NotBlank(message = "El número del destinatario no puede estar vacío")
    @Pattern(regexp = "^\\d{10,11}$", message = "El número debe tener entre 10 y 11 dígitos (incluye código de país sin '+')")
    private String recipient;

    @NotBlank(message = "El mensaje no puede estar vacío")
    @Size(max = 1000, message = "El mensaje no debe exceder los 1000 caracteres")
    private String message;

}
