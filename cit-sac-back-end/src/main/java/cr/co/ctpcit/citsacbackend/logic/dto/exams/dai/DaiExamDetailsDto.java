package cr.co.ctpcit.citsacbackend.logic.dto.exams.dai;

import cr.co.ctpcit.citsacbackend.data.enums.Recommendation;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;

/**
 * Represents the Data Transfer Object (DTO) for DAI exam details.
 * Contains information about the exam, a comment, a recommendation, and whether the exam has been reviewed.
 */
@Builder
public record DaiExamDetailsDto(Long id, ExamDaiDto exam, @Size(max = 255) String comment,
                                Recommendation recommendation, Boolean reviewed) implements Serializable {
}
