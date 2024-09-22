package cr.co.ctpcit.citsacbackend.logic.dto.dates;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO para {@link cr.co.ctpcit.citsacbackend.data.entities.ExamPeriodEntity}
 */


public record ExamPeriodDto(@NotNull Integer examPeriodId, @NotNull Date startDate,
                            @NotNull Date endDate) implements Serializable {
}
