package cr.co.ctpcit.citsacbackend.logic.dto.exams.academic;

import cr.co.ctpcit.citsacbackend.data.enums.ExamType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * Represents the Data Transfer Object (DTO) for an academic exam.
 * Contains information about the enrollment, exam date, type, and responses.
 */
@Builder
public record ExamAcaDto(Long id, @NotNull String enrollment,
                         @NotNull Instant examDate,
                         @NotNull ExamType examType, List<QuestionAcaDto> responses)
    implements Serializable {
}
