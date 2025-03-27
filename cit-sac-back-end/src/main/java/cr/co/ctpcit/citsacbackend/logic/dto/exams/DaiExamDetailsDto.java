package cr.co.ctpcit.citsacbackend.logic.dto.exams;

import cr.co.ctpcit.citsacbackend.data.enums.Recommendation;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.exams.DaiExamEntity}
 */
@Builder
public record DaiExamDetailsDto(Long id, ExamDaiDto exam, @Size(max = 255) String comment,
                                Recommendation recommendation) implements Serializable {
}
