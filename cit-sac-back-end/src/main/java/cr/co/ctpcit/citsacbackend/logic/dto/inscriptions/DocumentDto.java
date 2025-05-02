package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;

/**
 * Data Transfer Object (DTO) representing a document.
 * Contains details about the document's unique ID, URL postfix, type, and name.
 */
@Builder
public record DocumentDto(Long id, @Size(max = 32) String documentUrlPostfix,
                          @NotNull DocType documentType, String documentName)
    implements Serializable {
}
