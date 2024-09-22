package cr.co.ctpcit.citsacbackend.logic.dto.exams.dai;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO para {@link cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiExamQuestionsEntity}
 */
public record DaiExamQuestionsDto(@NotNull Integer examId, @NotNull Integer questionId,
                                  String studentAnswer) implements Serializable {
}
