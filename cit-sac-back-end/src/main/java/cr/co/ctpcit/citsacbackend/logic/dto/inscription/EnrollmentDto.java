package cr.co.ctpcit.citsacbackend.logic.dto.inscription;

import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.KnownThrough;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.inscription.EnrollmentEntity}
 */
@Builder
public record EnrollmentDto(@NotNull ProcessStatus status, LocalDateTime enrollmentDate, @NotNull Grades gradeToEnroll,
                            @NotNull KnownThrough knownThrough, @NotNull LocalDate examDate,
                            @NotNull Boolean consentGiven,
                            @NotNull Boolean whatsappNotification) implements Serializable {
}