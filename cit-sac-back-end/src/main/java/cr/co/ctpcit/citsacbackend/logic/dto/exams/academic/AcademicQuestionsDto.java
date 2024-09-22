package cr.co.ctpcit.citsacbackend.logic.dto.exams.academic;

import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO para {@link cr.co.ctpcit.citsacbackend.data.entities.exams.academic.AcademicQuestionsEntity}
 */
public record AcademicQuestionsDto(@NotNull Integer id, @NotNull String questionText,
                                   @NotNull Grades questionGrade, @NotNull String option_A,
                                   @NotNull String option_B, @NotNull String option_C,
                                   @NotNull String option_D, @NotNull String correctOption,
                                   String imageUrl) implements Serializable {
}
