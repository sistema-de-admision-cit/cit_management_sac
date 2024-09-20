package cr.co.ctpcit.citsacbackend.logic.dto.inscription;

import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.inscription.DocumentEntity}
 */
@Builder
public record DocumentDto(Long id,
                          @NotNull(message = "Es obligatorio que se indique el nombre del documento.")
                          @Size(max = 64)
                          @NotBlank(message = "Es obligatorio que se indique el nombre del documento.")
                          String documentName,
                          @NotNull(message = "Es obligatorio que se indique el tipo de documento.")
                          DocType documentType,
                          @NotNull(message = "Es obligatorio que se indique la URL del documento.")
                          @Size(max = 255)
                          @NotBlank(message = "Es obligatorio que se indique la URL del documento.")
                          String documentUrl) implements Serializable {
}