package cr.co.ctpcit.citsacbackend.logic.dto.dates;

import cr.co.ctpcit.citsacbackend.data.entities.config.ExamPeriodEntity;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO para {@link ExamPeriodEntity}
 */


public record ExamPeriodDto(@NotNull Integer examPeriodId, @NotNull Date startDate,
                            @NotNull Date endDate) implements Serializable {
}
