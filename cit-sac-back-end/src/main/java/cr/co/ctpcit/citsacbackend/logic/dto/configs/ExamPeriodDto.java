package cr.co.ctpcit.citsacbackend.logic.dto.configs;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Represents the Data Transfer Object (DTO) for an exam period.
 * Contains information about the start and end dates, a list of exam days, and whether the exams span the entire year.
 */
@Builder
public record ExamPeriodDto(
        Long id,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate,
        @NotNull List<ExamDayDto> examDays,
        @NotNull boolean allYear
) implements Serializable {
}

