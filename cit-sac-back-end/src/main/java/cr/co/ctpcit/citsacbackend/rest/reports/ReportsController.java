package cr.co.ctpcit.citsacbackend.rest.reports;

import cr.co.ctpcit.citsacbackend.logic.dto.reports.AdmissionFinalDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.EnrollmentAttendanceDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.ExamSourceDTO;
import cr.co.ctpcit.citsacbackend.logic.services.reports.ReportsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportsController {

  private final ReportsServiceImpl reportsService;

  public ReportsController(ReportsServiceImpl reportsService) {
    this.reportsService = reportsService;
  }

  private LocalDate parseDate(String s) {
    return (s == null || s.isBlank()) ? null : LocalDate.parse(s.trim());
  }

  /**
   * Example: GET /api/reports/exam-attendance?startDate=2025-01-01&endDate=2025-01-31
   * &grade=FIRST,SECOND &sector=Primaria
   */
  @GetMapping("/exam-attendance")
  public ResponseEntity<List<EnrollmentAttendanceDTO>> getExamAttendance(
      @RequestParam(value = "startDate", required = false) String startDateStr,
      @RequestParam(value = "endDate", required = false) String endDateStr,
      @RequestParam(value = "grade", defaultValue = "All") String grade,
      @RequestParam(value = "sector", defaultValue = "All") String sector) {
    LocalDate startDate = parseDate(startDateStr);
    LocalDate endDate = parseDate(endDateStr);


    List<String> grades =
        "All".equalsIgnoreCase(grade) ? List.of() : Arrays.asList(grade.split("\\s*,\\s*"));

    List<EnrollmentAttendanceDTO> report =
        reportsService.getEnrollmentAttendanceStats(startDate, endDate, grades, sector);

    System.out.println("Report: ");
    System.out.println(report);

    return ResponseEntity.ok(report);
  }

  @GetMapping("/exam-source")
  public ResponseEntity<List<ExamSourceDTO>> getExamSourceStatistics(
      @RequestParam(value = "startDate", required = false) String startDateStr,
      @RequestParam(value = "endDate", required = false) String endDateStr,
      @RequestParam(value = "grade", defaultValue = "All") String gradeCsv,
      @RequestParam(value = "sector", defaultValue = "All") String sector) {
    LocalDate startDate = parseDate(startDateStr);
    LocalDate endDate = parseDate(endDateStr);

    List<String> grades =
        "All".equalsIgnoreCase(gradeCsv) ? List.of() : Arrays.asList(gradeCsv.split("\\s*,\\s*"));

    List<ExamSourceDTO> result =
        reportsService.getExamSourceStatistics(startDate, endDate, grades, sector.trim());
    return ResponseEntity.ok(result);
  }

  /**
   * Example: GET /api/reports/admission-final?startDate=2025-01-01&endDate=2025-01-31
   *
   * @param startDateStr
   * @param endDateStr
   * @param grade
   * @param sector
   * @return
   */
  @GetMapping("/admission-final")
  public ResponseEntity<List<AdmissionFinalDTO>> getAdmissionFinal(
      @RequestParam String startDateStr, @RequestParam String endDateStr,
      @RequestParam(defaultValue = "All") String grade,
      @RequestParam(defaultValue = "All") String sector) {
    LocalDate startDate = parseDate(startDateStr);
    LocalDate endDate = parseDate(endDateStr);

    List<String> grades =
        "All".equalsIgnoreCase(grade) ? List.of() : Arrays.asList(grade.split("\\s*,\\s*"));

    List<AdmissionFinalDTO> result =
        reportsService.getAdmissionFinalStats(startDate, endDate, grades, sector.trim());

    return ResponseEntity.ok(result);
  }
}

