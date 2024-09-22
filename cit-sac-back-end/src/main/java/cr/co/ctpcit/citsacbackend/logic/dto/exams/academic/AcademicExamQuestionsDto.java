package cr.co.ctpcit.citsacbackend.logic.dto.exams.academic;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO para {@link cr.co.ctpcit.citsacbackend.data.entities.exams.academic.AcademicExamQuestionsEntity}
 */

public record AcademicExamQuestionsDto(
        @NotNull Integer examId,
        @NotNull Integer questionId,
        String studentAnswer
) implements Serializable {
}