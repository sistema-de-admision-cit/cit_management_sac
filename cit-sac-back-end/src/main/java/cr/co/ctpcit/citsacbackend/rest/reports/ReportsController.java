package cr.co.ctpcit.citsacbackend.rest.reports;


import cr.co.ctpcit.citsacbackend.data.utils.CsvReportGenerator;
import cr.co.ctpcit.citsacbackend.data.utils.PdfReportGenerator;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.*;
import cr.co.ctpcit.citsacbackend.logic.services.reports.ReportsServiceImpl;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;
/**
 * REST controller for generating various system reports.
 * Provides endpoints for retrieving exam-related statistics and attendance reports.
 */
@RestController
@RequestMapping("/api/reports")
public class ReportsController {

  private final PdfReportGenerator pdfReportGenerator;
  private final CsvReportGenerator csvReportGenerator;


  private final ReportsServiceImpl reportsService;
  /**
   * Constructs a new ReportsController with the specified ReportsServiceImpl.
   *
   * @param reportsService the service to handle report generation
   */
  public ReportsController(PdfReportGenerator pdfReportGenerator, CsvReportGenerator csvReportGenerator, ReportsServiceImpl reportsService) {
      this.pdfReportGenerator = pdfReportGenerator;
      this.csvReportGenerator = csvReportGenerator;
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


  /**
   * Generates a PDF report based on the provided report request and returns it as a downloadable file.
   *
   * <p>This endpoint accepts a {@link ReportRequestDto} in the request body, retrieves the relevant report data,
   * and uses the {@code pdfReportGenerator} to generate a PDF document. The generated file is returned as a
   * byte array in the response, with appropriate headers for file download.
   *
   * @param request the {@link ReportRequestDto} containing the parameters for report generation
   * @return a {@link ResponseEntity} containing the PDF file as a byte array and download headers
   */
  @PostMapping("/generate/pdf")
  public ResponseEntity<byte[]> generatePdfReport(@RequestBody ReportRequestDto request) {
    List<ReportDataDto> reportData = reportsService.generateReportData(request);
    byte[] pdfBytes = pdfReportGenerator.generateReport(reportData, request);

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdfBytes);
  }

  /**
   * Generates a CSV report based on the provided report request and returns it as a downloadable file.
   *
   * <p>This endpoint accepts a {@link ReportRequestDto} in the request body, retrieves the relevant report data,
   * and uses the {@code csvReportGenerator} to generate a CSV file. The generated file is returned as a
   * byte array in the response, with appropriate headers for file download.
   *
   * @param request the {@link ReportRequestDto} containing the parameters for report generation
   * @return a {@link ResponseEntity} containing the CSV file as a byte array and download headers
   */
  @PostMapping("/generate/csv")
  public ResponseEntity<byte[]> generateCsvReport(@RequestBody ReportRequestDto request) {
    List<ReportDataDto> reportData = reportsService.generateReportData(request);
    byte[] csvBytes = csvReportGenerator.generateReport(reportData, request);

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.csv")
            .contentType(MediaType.parseMediaType("text/csv; charset=UTF-8"))
            .body(csvBytes);
  }


}

