package cr.co.ctpcit.citsacbackend.logic.dto.configs;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.configs.ExamPeriodEntity}
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

