package cr.co.ctpcit.citsacbackend.logic.services.reports;

import cr.co.ctpcit.citsacbackend.data.repositories.reports.ReportsRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportsServiceImpl {
  private final ReportsRepository reportsRepository;

  public ReportsServiceImpl(ReportsRepository reportsRepository) {
    this.reportsRepository = reportsRepository;
  }

  public List<ExamSourceDTO> getExamSourceStatistics(LocalDate startDate, LocalDate endDate,
      List<String> grades, String sector) {
    return reportsRepository.findExamSourceStatistics(startDate, endDate, grades, sector);
  }

  public List<EnrollmentAttendanceDTO> getEnrollmentAttendanceStats(LocalDate startDate,
      LocalDate endDate, List<String> grades, String sector) {
    return reportsRepository.findEnrollmentAttendanceStats(startDate, endDate, grades, sector);
  }

  public List<AdmissionFinalDTO> getAdmissionFinalStats(LocalDate startDate, LocalDate endDate,
      List<String> grades, String sector) {
    return reportsRepository.findAdmissionFinalStats(startDate, endDate, grades, sector);
  }

  public AcademicExamReportDTO getAcademicExamReport(LocalDate startDate, LocalDate endDate,
      List<String> grades, String sector) {

    List<AcademicDistributionDTO> dist =
        reportsRepository.findAcademicExamDistribution(startDate, endDate, grades, sector);
    List<AcademicGradeAverageDTO> avg =
        reportsRepository.findAcademicExamGradeAverages(startDate, endDate, grades, sector);

    return new AcademicExamReportDTO(dist, avg);
  }

  public DaiExamReportDTO getDaiExamReport(LocalDate startDate, LocalDate endDate,
      List<String> grades, String sector) {

    List<DaiDetailDTO> details = reportsRepository.findDaiExamDetails(startDate, endDate, grades, sector);
    List<DaiAreaAverageDTO> avgs = reportsRepository.findDaiExamAreaAverages(startDate, endDate, grades, sector);

    return new DaiExamReportDTO(details, avgs);
  }
}
