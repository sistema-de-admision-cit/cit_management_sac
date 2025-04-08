package cr.co.ctpcit.citsacbackend.logic.dto.configs;

import cr.co.ctpcit.citsacbackend.data.enums.WeekDays;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.configs.ExamDayEntity}
 */
@Builder
public record ExamDayDto(Long id, @NotNull WeekDays examDay, @NotNull LocalTime startTime)
    implements Serializable {
}
