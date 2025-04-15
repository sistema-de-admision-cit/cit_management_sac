package cr.co.ctpcit.citsacbackend.logic.dto.exams.academic;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Represents the Data Transfer Object (DTO) for academic exam details.
 * Contains information about the exam, its unique identifier, and the grade achieved.
 */
@Builder
public record AcademicExamDetailsDto(Long id, ExamAcaDto exam, @NotNull BigDecimal grade)
    implements Serializable {
}
