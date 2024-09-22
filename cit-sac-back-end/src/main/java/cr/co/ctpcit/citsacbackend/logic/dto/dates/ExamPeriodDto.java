package cr.co.ctpcit.citsacbackend.logic.dto.dates;

import java.util.Date;
import java.io.Serializable;
import jakarta.validation.constraints.NotNull;
/**
 * DTO para {@link cr.co.ctpcit.citsacbackend.data.entities.ExamPeriodEntity}
 */


public record ExamPeriodDto(
        @NotNull Integer examPeriodId,
        @NotNull Date startDate,
        @NotNull Date endDate
) implements Serializable {
}