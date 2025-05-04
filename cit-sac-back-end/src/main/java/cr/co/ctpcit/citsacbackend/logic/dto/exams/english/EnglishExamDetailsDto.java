package cr.co.ctpcit.citsacbackend.logic.dto.exams.english;

import cr.co.ctpcit.citsacbackend.data.enums.EnglishLevel;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.time.Instant;

/**
 * Represents the Data Transfer Object (DTO) for the details of an English exam.
 * Contains information about the exam ID, enrollment ID, exam date, track test ID, level, and core.
 */
@Builder
public record EnglishExamDetailsDto(Long examId, Long enrollmentId,
                                    Instant examDate, Long trackTestId,
                                    @NotNull EnglishLevel level, @NotNull Byte core)
    implements Serializable {
}
