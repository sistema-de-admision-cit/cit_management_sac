package cr.co.ctpcit.citsacbackend.logic.dto.exams;

import com.fasterxml.jackson.annotation.JsonFormat;
import cr.co.ctpcit.citsacbackend.data.enums.ExamType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.exams.ExamEntity}
 */
@Builder
public record ExamAcaDto(Long id, @NotNull Long enrollment,
                         @NotNull Instant examDate,
                         @NotNull ExamType examType, List<QuestionAcaDto> responses)
    implements Serializable {
}
