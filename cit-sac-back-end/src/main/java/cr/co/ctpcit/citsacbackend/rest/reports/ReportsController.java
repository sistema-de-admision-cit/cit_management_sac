package cr.co.ctpcit.citsacbackend.rest.reports;

import cr.co.ctpcit.citsacbackend.logic.dto.reports.AdmissionFinalDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.EnrollmentAttendanceDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.ExamSourceDTO;
import cr.co.ctpcit.citsacbackend.logic.services.reports.ReportsServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
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

  /**
   * Example: GET /api/reports/exam-attendance?startDate=2025-01-01&endDate=2025-01-31
   * &grade=FIRST,SECOND &sector=Primaria
   */
  @GetMapping("/exam-attendance")
  public ResponseEntity<List<EnrollmentAttendanceDTO>> getExamAttendance(
      @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,

      @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,

      @RequestParam(value = "grade", defaultValue = "All") String gradeCsv,

      @RequestParam(value = "sector", defaultValue = "All") String sector) {
    // Convertimos CSV de grados en lista; si viene "All" o vacío, pasamos lista vacía
    List<String> grades =
        "All".equalsIgnoreCase(gradeCsv) ? List.of() : Arrays.asList(gradeCsv.split(","));

    List<EnrollmentAttendanceDTO> report =
        reportsService.getEnrollmentAttendanceStats(startDate, endDate, grades, sector);

    System.out.println("Report: ");
    System.out.println(report);

    return ResponseEntity.ok(report);
  }

  @GetMapping("/exam-source")
  public ResponseEntity<List<ExamSourceDTO>> getExamSourceStatistics() {
    List<ExamSourceDTO> result = reportsService.getExamSourceStatistics();
    return ResponseEntity.ok(result);
  }

  /**
   * Example: GET /api/reports/admission-final?startDate=2025-01-01&endDate=2025-01-31
   * @param startDate
   * @param endDate
   * @param grade
   * @param sector
   * @return
   */
  @GetMapping("/admission-final")
  public ResponseEntity<List<AdmissionFinalDTO>> getAdmissionFinal(@RequestParam String startDate,
      @RequestParam String endDate, @RequestParam(defaultValue = "All") String grade,
      @RequestParam(defaultValue = "All") String sector) {
    LocalDate sd = startDate.isBlank() ? null : LocalDate.parse(startDate.trim());
    LocalDate ed = endDate.isBlank() ? null : LocalDate.parse(endDate.trim());
    List<String> grades =
        "All".equalsIgnoreCase(grade) ? List.of() : Arrays.asList(grade.split("\\s*,\\s*"));
    List<AdmissionFinalDTO> result =
        reportsService.getAdmissionFinalStats(sd, ed, grades, sector.trim());
    return ResponseEntity.ok(result);
  }
}

