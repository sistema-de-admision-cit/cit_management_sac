package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.inscriptions.DocumentEntity}
 */
@Builder
public record DocumentDto(Long id, @Size(max = 32) String documentName,
                          @NotNull DocType documentType)
    implements Serializable {
}
