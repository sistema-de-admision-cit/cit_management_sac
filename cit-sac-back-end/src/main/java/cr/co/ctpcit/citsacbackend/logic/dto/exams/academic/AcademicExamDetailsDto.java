package cr.co.ctpcit.citsacbackend.logic.dto.exams.academic;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.exams.AcademicExamEntity}
 */
@Builder
public record AcademicExamDetailsDto(Long id, ExamAcaDto exam, @NotNull BigDecimal grade)
    implements Serializable {
}
