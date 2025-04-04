package cr.co.ctpcit.citsacbackend.logic.dto.exams.academic;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record QuestionOptionAcaDto(Long id, @NotNull Boolean isCorrect,
                                   @NotNull @Size(max = 255) String option, Boolean selected) {
}
