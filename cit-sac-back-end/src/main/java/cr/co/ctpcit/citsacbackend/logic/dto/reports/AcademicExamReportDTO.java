package cr.co.ctpcit.citsacbackend.logic.dto.reports;

import java.util.List;

public record AcademicExamReportDTO(List<AcademicDistributionDTO> distribution,
                                    List<AcademicGradeAverageDTO> gradeAverages) {
}
