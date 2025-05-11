package cr.co.ctpcit.citsacbackend.logic.dto.reports;

import java.math.BigDecimal;

public record PreviousGradesStatusDTO(
    BigDecimal previousGrades,
    String status
) {
}
