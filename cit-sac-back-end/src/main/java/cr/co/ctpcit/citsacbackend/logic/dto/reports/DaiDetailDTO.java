package cr.co.ctpcit.citsacbackend.logic.dto.reports;

import java.math.BigDecimal;

public record DaiDetailDTO(
    Long enrollmentId,
    String area,
    BigDecimal score
) {}
