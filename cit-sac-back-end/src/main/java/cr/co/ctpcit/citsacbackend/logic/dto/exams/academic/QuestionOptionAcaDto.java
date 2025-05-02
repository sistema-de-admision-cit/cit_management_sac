package cr.co.ctpcit.citsacbackend.logic.dto.exams.academic;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Represents the Data Transfer Object (DTO) for an option in an academic exam question.
 * Contains information about the option text, whether it is correct, and whether it was selected.
 */
public record QuestionOptionAcaDto(Long id, @NotNull Boolean isCorrect,
                                   @NotNull @Size(max = 255) String option, Boolean selected) {
}
