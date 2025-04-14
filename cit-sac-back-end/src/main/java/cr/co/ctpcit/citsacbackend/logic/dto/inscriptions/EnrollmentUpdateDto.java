package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for update {@link EnrollmentEntity}
 */
public record EnrollmentUpdateDto(LocalDate examDate,
                                  ProcessStatus processStatus,
                                  Boolean whatsappPermission,
                                  BigDecimal previousGrades,
                                  String comment,
                                  Integer changedBy) {
}
