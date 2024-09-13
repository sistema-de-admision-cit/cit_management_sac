package cr.co.ctpcit.citsacbackend.logic.dto.exams.dai;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO para {@link cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiGradesEntity}
 */
public record DaiGradesDto(
        @NotNull Integer id,
        @NotNull Integer enrollmentId
) implements Serializable {
}
