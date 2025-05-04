package cr.co.ctpcit.citsacbackend.logic.dto.reports;

import java.time.LocalDate;

public record AdmissionFinalDTO(LocalDate enrollmentDate, String grade, String sector,
                                int totalAccepted, int totalRejected) {
}
