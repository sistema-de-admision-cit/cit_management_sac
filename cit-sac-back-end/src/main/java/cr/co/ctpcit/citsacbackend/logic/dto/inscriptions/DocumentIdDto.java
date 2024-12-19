package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import java.io.Serializable;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.inscriptions.DocumentEntityId}
 */
public record DocumentIdDto(Long documentId, Long enrollmentId) implements Serializable {
}
