package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.inscriptions.DocumentEntity}
 */
public record DocumentDto(DocumentIdDto id, @Size(max = 32) String documentName,
                          @NotNull DocType documentType)
    implements Serializable {
}
