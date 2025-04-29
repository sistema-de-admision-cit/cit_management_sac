package cr.co.ctpcit.citsacbackend.logic.dto.configs;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object (DTO) for representing an exam period.
 * This class is used to transfer exam period data between different layers of the application.
 * It corresponds to the {@link cr.co.ctpcit.citsacbackend.data.entities.configs.ExamPeriodEntity} entity.
 */
@Builder
public record ExamPeriodDto(
        /**
         * The unique identifier of the exam period.
         * This field is null when creating a new exam period.
         */
        Long id,

        /**
         * The start date of the exam period.
         * Cannot be null.
         */
        @NotNull LocalDate startDate,

        /**
         * The end date of the exam period.
         * Cannot be null.
         */
        @NotNull LocalDate endDate,

        /**
         * The list of exam days within this period.
         * Each exam day represents a specific day when exams are scheduled.
         * Cannot be null.
         */
        @NotNull List<ExamDayDto> examDays,

        /**
         * Indicates whether this exam period applies to the entire year.
         * Cannot be null.
         */
        @NotNull boolean allYear
) implements Serializable {
}
