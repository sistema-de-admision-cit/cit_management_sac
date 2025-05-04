package cr.co.ctpcit.citsacbackend.rest.reports;

import cr.co.ctpcit.citsacbackend.logic.dto.reports.*;
import cr.co.ctpcit.citsacbackend.logic.services.reports.ReportsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Arrays;
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

  private LocalDate parseDate(String s) {
    return (s == null || s.isBlank()) ? null : LocalDate.parse(s.trim());
  }

  // parseGrades function
  private List<String> parseGrades(String grades) {
    return "All".equalsIgnoreCase(grades) ? List.of() : Arrays.asList(grades.split("\\s*,\\s*"));
  }

  /**
   * Example: GET /api/reports/exam-attendance?startDate=2025-01-01&endDate=2025-01-31
   * &grade=FIRST,SECOND &sector=Primaria
   */
  /**
   * Retrieves an exam attendance report filtered by date range, grade, and sector.
   *
   * @param dateRangeStart the start date of the reporting period
   * @param dateRangeEnd the end date of the reporting period
   * @param grade the grade level to filter by
   * @param sector the educational sector to filter by
   * @return ResponseEntity containing the attendance report as a String
   */
  @GetMapping("/exam-attendance")
  public ResponseEntity<List<EnrollmentAttendanceDTO>> getExamAttendance(
      @RequestParam(value = "startDate", required = false) String startDateStr,
      @RequestParam(value = "endDate", required = false) String endDateStr,
      @RequestParam(value = "grade", defaultValue = "All") String grade,
      @RequestParam(value = "sector", defaultValue = "All") String sector) {
    LocalDate startDate = parseDate(startDateStr);
    LocalDate endDate = parseDate(endDateStr);


    List<String> grades = parseGrades(grade);

    List<EnrollmentAttendanceDTO> report =
        reportsService.getEnrollmentAttendanceStats(startDate, endDate, grades, sector);

    System.out.println("Report: ");
    System.out.println(report);

    return ResponseEntity.ok(report);
  }
  /**
   * Retrieves statistics about exam sources.
   *
   * @return ResponseEntity containing a list of ExamSourceDTO with exam source statistics
   */
  @GetMapping("/exam-source")
  public ResponseEntity<List<ExamSourceDTO>> getExamSourceStatistics(
      @RequestParam(value = "startDate", required = false) String startDateStr,
      @RequestParam(value = "endDate", required = false) String endDateStr,
      @RequestParam(value = "grade", defaultValue = "All") String gradeCsv,
      @RequestParam(value = "sector", defaultValue = "All") String sector) {
    LocalDate startDate = parseDate(startDateStr);
    LocalDate endDate = parseDate(endDateStr);

    List<String> grades = parseGrades(gradeCsv);


    List<ExamSourceDTO> result =
        reportsService.getExamSourceStatistics(startDate, endDate, grades, sector.trim());
    return ResponseEntity.ok(result);
  }

  /**
   * Example: GET /api/reports/admission-final?startDate=2025-01-01&endDate=2025-01-31
   *
   * @param startDateStr
   * @param endDateStr
   * @param gradeCsv
   * @param sector
   * @return
   */
  @GetMapping("/admission-final")
  public ResponseEntity<List<AdmissionFinalDTO>> getAdmissionFinal(
      @RequestParam(value = "startDate", required = false) String startDateStr,
      @RequestParam(value = "endDate", required = false) String endDateStr,
      @RequestParam(value = "grade", defaultValue = "All") String gradeCsv,
      @RequestParam(value = "sector", defaultValue = "All") String sector) {
    LocalDate startDate = parseDate(startDateStr);
    LocalDate endDate = parseDate(endDateStr);

    List<String> grades = parseGrades(gradeCsv);

    List<AdmissionFinalDTO> result =
        reportsService.getAdmissionFinalStats(startDate, endDate, grades, sector.trim());

    return ResponseEntity.ok(result);
  }

  /**
   * Example: GET /api/reports/academic?startDate=2025-01-01&endDate=2025-01-31
   * @param start
   * @param end
   * @param gradeCsv
   * @param sector
   * @return
   */
  @GetMapping("/academic")
  public ResponseEntity<AcademicExamReportDTO> getAcademicReport(
      @RequestParam(value = "startDate", required = false) String start,
      @RequestParam(value = "endDate", required = false) String end,
      @RequestParam(value = "grade", defaultValue = "All") String gradeCsv,
      @RequestParam(value = "sector", defaultValue = "All") String sector) {
    LocalDate sd = parseDate(start);
    LocalDate ed = parseDate(end);
    List<String> grades = parseGrades(gradeCsv);
    AcademicExamReportDTO dto = reportsService.getAcademicExamReport(sd, ed, grades, sector.trim());
    return ResponseEntity.ok(dto);
  }

  // --- DAI composite endpoint ---
  @GetMapping("/dai")
  public ResponseEntity<DaiExamReportDTO> getDaiReport(
      @RequestParam(value = "startDate", required = false) String start,
      @RequestParam(value = "endDate", required = false) String end,
      @RequestParam(value = "grade", defaultValue = "All") String gradeCsv,
      @RequestParam(value = "sector", defaultValue = "All") String sector) {
    LocalDate sd = parseDate(start);
    LocalDate ed = parseDate(end);
    List<String> grades = parseGrades(gradeCsv);
    DaiExamReportDTO dto = reportsService.getDaiExamReport(sd, ed, grades, sector.trim());
    return ResponseEntity.ok(dto);
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

