package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.KnownThrough;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link EnrollmentEntity}
 */
public record EnrollmentDto(Long id, @NotNull StudentDto student, @NotNull ProcessStatus status,
                            @NotNull Instant enrollmentDate, @NotNull Grades gradeToEnroll,
                            @NotNull KnownThrough knownThrough, @NotNull LocalDate examDate,
                            @NotNull Boolean consentGiven, @NotNull Boolean whatsappNotification,
                            List<DocumentDto> documents) implements Serializable {
}
