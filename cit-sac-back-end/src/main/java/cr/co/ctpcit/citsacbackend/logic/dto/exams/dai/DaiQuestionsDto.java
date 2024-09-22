package cr.co.ctpcit.citsacbackend.logic.dto.exams.dai;

import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO para {@link cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiQuestionsEntity}
 */
public record DaiQuestionsDto(
        @NotNull Integer id,
        @NotNull String questionText,
        @NotNull Grades questionGrade,
        String imageUrl
) implements Serializable {
}