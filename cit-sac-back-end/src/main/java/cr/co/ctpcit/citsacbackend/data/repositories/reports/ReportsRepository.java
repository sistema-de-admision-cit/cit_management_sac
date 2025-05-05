package cr.co.ctpcit.citsacbackend.data.repositories.reports;

import cr.co.ctpcit.citsacbackend.logic.dto.reports.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ReportsRepository {
  List<ExamSourceDTO> findExamSourceStatistics(LocalDate startDate, LocalDate endDate,
      List<String> gradesCsv, String sector);

  List<EnrollmentAttendanceDTO> findEnrollmentAttendanceStats(LocalDate startDate,
      LocalDate endDate, List<String> grades, String sector);

  List<AdmissionFinalDTO> findAdmissionFinalStats(LocalDate startDate, LocalDate endDate,
      List<String> grades, String sector);

  List<AcademicDistributionDTO> findAcademicExamDistribution(LocalDate startDate, LocalDate endDate,
      List<String> grades, String sector);

  List<AcademicGradeAverageDTO> findAcademicExamGradeAverages(LocalDate startDate,
      LocalDate endDate, List<String> grades, String sector);
}

