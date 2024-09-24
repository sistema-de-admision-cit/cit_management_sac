package cr.co.ctpcit.citsacbackend.logic.dto.dates;

import java.util.List;

public record ConfigDateDTO(Boolean allYear, String startDate, String endDate,
                            List<String> applicationDays, String startTime) {
}
