package cr.co.ctpcit.citsacbackend.logic.dto.exams.english;

import cr.co.ctpcit.citsacbackend.data.enums.EnglishLevel;
import cr.co.ctpcit.citsacbackend.data.enums.ExamType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.exams.EnglishExamEntity}
 */
@Builder
public record EnglishExamDetailsDto(Long examId, Long enrollmentId,
                                    Instant examDate, Long trackTestId,
                                    @NotNull EnglishLevel level, @NotNull Byte core)
    implements Serializable {
}
