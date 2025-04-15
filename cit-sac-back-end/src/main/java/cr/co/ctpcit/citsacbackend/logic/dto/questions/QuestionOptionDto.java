package cr.co.ctpcit.citsacbackend.logic.dto.questions;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionOptionEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * Data transfer object (DTO) representing a question option in an exam.
 * This DTO contains the details of an option associated with a question.
 * It includes the option's ID, correctness status, and the option's text.
 */
public record QuestionOptionDto(Long id, @NotNull Boolean isCorrect,
                                @NotNull @Size(max = 255) String option) implements Serializable {
}
