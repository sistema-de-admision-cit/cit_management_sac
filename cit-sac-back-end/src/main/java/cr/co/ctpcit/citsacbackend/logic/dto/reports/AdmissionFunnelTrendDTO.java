package cr.co.ctpcit.citsacbackend.logic.dto.reports;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AdmissionFunnelTrendDTO(LocalDate enrollmentDate, Integer interestedCount,
                                      Integer eligibleCount, Integer acceptedCount,
                                      BigDecimal pctInterestedToEligible,
                                      BigDecimal pctEligibleToAccepted) {
}
