package cr.co.ctpcit.citsacbackend.logic.dto.questions;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionOptionEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link QuestionOptionEntity}
 */
public record QuestionOptionDto(Long id, @NotNull Boolean isCorrect,
                                @NotNull @Size(max = 255) String option) implements Serializable {
}
