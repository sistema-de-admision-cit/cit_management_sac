package cr.co.ctpcit.citsacbackend.logic.dto.dates;

import cr.co.ctpcit.citsacbackend.data.entities.config.ExamDayEntity;
import cr.co.ctpcit.citsacbackend.data.enums.WeekDays;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO para {@link ExamDayEntity}
 */

public record ExamDayDto(@NotNull Integer examDayId, @NotNull Integer examPeriodId,
                         @NotNull WeekDays examDay, @NotNull String startTime)
    implements Serializable {
}
