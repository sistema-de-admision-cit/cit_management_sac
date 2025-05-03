package cr.co.ctpcit.citsacbackend.logic.dto.reports;

import java.util.List;

public record DaiExamReportDTO(List<DaiDetailDTO> details, List<DaiAreaAverageDTO> areaAverages) {
}
