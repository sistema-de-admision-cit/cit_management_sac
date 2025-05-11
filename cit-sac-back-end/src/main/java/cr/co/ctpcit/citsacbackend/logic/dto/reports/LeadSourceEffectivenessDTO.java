package cr.co.ctpcit.citsacbackend.logic.dto.reports;

import java.math.BigDecimal;

public record LeadSourceEffectivenessDTO(String examSource, int studentCount,
                                         BigDecimal acceptanceRate, BigDecimal avgExamScore) {
}
