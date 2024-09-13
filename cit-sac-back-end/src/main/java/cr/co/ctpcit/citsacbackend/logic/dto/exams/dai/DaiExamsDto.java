package cr.co.ctpcit.citsacbackend.logic.dto.exams.dai;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO para {@link cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiExamsEntity}
 */
public record DaiExamsDto(
        @NotNull Integer id,
        @NotNull Date examDate,
        @NotNull BigDecimal grade
) implements Serializable {
}