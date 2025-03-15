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

@RestController
@RequestMapping("/api/reports")
public class ReportsController {

  private ReportsServiceImpl reportsService;

  public ReportsController(ReportsServiceImpl reportsService) {
    this.reportsService = reportsService;
  }

  //  GET /api/reports/exam-attendance?dateRange=YYYY-MM-DD&grade=X&sector=primary
  @GetMapping("/exam-attendance")
  public ResponseEntity<String> getExamAttendance(@RequestParam Date dateRangeStart,
      @RequestParam Date dateRangeEnd, @RequestParam String grade, @RequestParam String sector) {
    String report =
        "Exam attendance report for grade %s in sector %s from %s to %s" + grade + sector + dateRangeStart + dateRangeEnd;

    return ResponseEntity.ok(report);
  }

  @GetMapping("/exam-source")
  public ResponseEntity<List<ExamSourceDTO>> getExamSourceStatistics() {
    List<ExamSourceDTO> result = reportsService.getExamSourceStatistics();
    return ResponseEntity.ok(result);
  }
}

