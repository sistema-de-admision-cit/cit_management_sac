package cr.co.ctpcit.citsacbackend.logic.dto.inscription;

import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.KnownThrough;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.inscription.EnrollmentEntity}
 */
@Builder
public record EnrollmentDto(Long id, ProcessStatus status, @NotNull(
    message = "Es obligatorio que se indique el grado al que se desea matricular") Grades gradeToEnroll,
                            @NotNull(
                                message = "Es obligatorio que se indique cómo se enteró de nosotros") KnownThrough knownThrough,
                            @NotNull(
                                message = "Es obligatorio que se indique la fecha del examen") LocalDate examDate,
                            @NotNull(
                                message = "Es obligatorio que se indique si se dio el consentimiento") Boolean consentGiven,
                            @NotNull(
                                message = "Es obligatorio que se indique si se dio el consentimiento para notificaciones por WhatsApp") Boolean whatsappNotification,
                            List<DocumentDto> document) implements Serializable {
}
