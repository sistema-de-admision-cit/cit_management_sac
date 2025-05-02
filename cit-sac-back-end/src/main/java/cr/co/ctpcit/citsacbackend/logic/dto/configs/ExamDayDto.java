package cr.co.ctpcit.citsacbackend.logic.dto.configs;

import cr.co.ctpcit.citsacbackend.data.enums.WeekDays;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * Represents the Data Transfer Object (DTO) for an exam day.
 * Contains information about the day of the week and the start time of the exam.
 */
@Builder
public record ExamDayDto(Long id, @NotNull WeekDays examDay, @NotNull LocalTime startTime)
    implements Serializable {
}
