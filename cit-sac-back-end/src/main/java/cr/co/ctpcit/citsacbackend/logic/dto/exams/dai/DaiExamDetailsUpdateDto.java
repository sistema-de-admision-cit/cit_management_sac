package cr.co.ctpcit.citsacbackend.logic.dto.exams.dai;

import cr.co.ctpcit.citsacbackend.data.enums.Recommendation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record DaiExamDetailsUpdateDto(Long id, @Size(max = 255) @NotNull String comment,
                                      @NotNull Recommendation recommendation) implements Serializable {
}
