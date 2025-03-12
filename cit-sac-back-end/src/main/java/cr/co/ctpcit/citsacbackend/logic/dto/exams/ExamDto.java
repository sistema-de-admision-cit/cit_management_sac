package cr.co.ctpcit.citsacbackend.logic.dto.exams;

import cr.co.ctpcit.citsacbackend.data.enums.ExamType;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.exams.ExamEntity}
 */
@Builder
public record ExamDto(Long id, @NotNull Long enrollment, @NotNull Instant examDate,
                      @NotNull ExamType examType, List<Question> responses)
    implements Serializable {
}
