package cr.co.ctpcit.citsacbackend.logic.dto.reports;

import java.math.BigDecimal;

public record AcademicGradeAverageDTO(
    String grade,
    BigDecimal averageScore
) {}
