package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.KnownThrough;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing the enrollment details for a student.
 * Contains information about the student, their enrollment status, grades, exam date, consent, and notifications.
 */
@Builder
public record EnrollmentDto(Long id, @NotNull StudentDto student, ProcessStatus status,
                            @NotNull Grades gradeToEnroll, @NotNull KnownThrough knownThrough,
                            @NotNull LocalDate examDate, @NotNull Boolean consentGiven,
                            @NotNull Boolean whatsappNotification, List<DocumentDto> documents)
    implements Serializable {
}
