package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing the update information for an enrollment.
 * Contains details about the updated exam date, process status, permissions, grades, comments, and the user who made the changes.
 */
public record EnrollmentUpdateDto(LocalDate examDate,
                                  ProcessStatus status,
                                  Boolean whatsappPermission,
                                  BigDecimal previousGrades,
                                  String comment,
                                  String changedBy) {
}
