package cr.co.ctpcit.citsacbackend.logic.dto.dates;

import cr.co.ctpcit.citsacbackend.data.enums.WeekDays;
import java.io.Serializable;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para {@link cr.co.ctpcit.citsacbackend.data.entities.ExamDayEntity}
 */

public record ExamDayDto (
    @NotNull Integer examDayId,
    @NotNull Integer examPeriodId,
    @NotNull WeekDays examDay,
    @NotNull String startTime
) implements Serializable {
    }