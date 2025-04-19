package cr.co.ctpcit.citsacbackend.rest.reports;

import cr.co.ctpcit.citsacbackend.logic.dto.reports.ExamSourceDTO;
import cr.co.ctpcit.citsacbackend.logic.services.reports.ReportsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
/**
 * REST controller for generating various system reports.
 * Provides endpoints for retrieving exam-related statistics and attendance reports.
 */
@RestController
@RequestMapping("/api/reports")
public class ReportsController {

  private final ReportsServiceImpl reportsService;
  /**
   * Constructs a new ReportsController with the specified ReportsServiceImpl.
   *
   * @param reportsService the service to handle report generation
   */
  public ReportsController(ReportsServiceImpl reportsService) {
    this.reportsService = reportsService;
  }
  /**
   * Retrieves an exam attendance report filtered by date range, grade, and sector.
   *
   * @param dateRangeStart the start date of the reporting period
   * @param dateRangeEnd the end date of the reporting period
   * @param grade the grade level to filter by
   * @param sector the educational sector to filter by
   * @return ResponseEntity containing the attendance report as a String
   */
  //  GET /api/reports/exam-attendance?dateRange=YYYY-MM-DD&grade=X&sector=primary
  @GetMapping("/exam-attendance")
  public ResponseEntity<String> getExamAttendance(@RequestParam Date dateRangeStart,
      @RequestParam Date dateRangeEnd, @RequestParam String grade, @RequestParam String sector) {
    String report =
        "Exam attendance report for grade %s in sector %s from %s to %s" + grade + sector + dateRangeStart + dateRangeEnd;

    return ResponseEntity.ok(report);
  }
  /**
   * Retrieves statistics about exam sources.
   *
   * @return ResponseEntity containing a list of ExamSourceDTO with exam source statistics
   */
  @GetMapping("/exam-source")
  public ResponseEntity<List<ExamSourceDTO>> getExamSourceStatistics() {
    List<ExamSourceDTO> result = reportsService.getExamSourceStatistics();
    return ResponseEntity.ok(result);
  }
}

