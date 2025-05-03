package cr.co.ctpcit.citsacbackend.logic.services.reports;

import cr.co.ctpcit.citsacbackend.data.repositories.reports.ReportsRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.AdmissionFinalDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.EnrollmentAttendanceDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.ExamSourceDTO;
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
}
