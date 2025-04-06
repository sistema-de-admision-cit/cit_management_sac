package cr.co.ctpcit.citsacbackend.logic.dto.exams.dai;

import cr.co.ctpcit.citsacbackend.data.enums.Recommendation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.exams.DaiExamEntity}
 */
@Builder
public record UpdateDaiExamDto(Long id, @Size(max = 255) String comment,
                               @NotNull Recommendation recommendation) implements Serializable {
}
