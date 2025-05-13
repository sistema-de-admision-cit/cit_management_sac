package cr.co.ctpcit.citsacbackend.rest.reports;

import cr.co.ctpcit.citsacbackend.TestProvider;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.data.enums.ReportType;
import cr.co.ctpcit.citsacbackend.data.utils.CsvReportGenerator;
import cr.co.ctpcit.citsacbackend.data.utils.PdfReportGenerator;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.*;
import cr.co.ctpcit.citsacbackend.logic.services.reports.ReportsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportControllerTest {

    @Mock
    private ReportsServiceImpl reportsService;

    @Mock
    private PdfReportGenerator pdfReportGenerator;

    @Mock
    private CsvReportGenerator csvReportGenerator;

    @InjectMocks
    private ReportsController reportController;

    private ReportRequestDto request;
    private List<ReportDataDto> mockReportData;

    private final String TEST_START_DATE = "2025-01-01";
    private final String TEST_END_DATE = "2025-01-31";
    private final String TEST_GRADE = "FIRST,SECOND";
    private final String TEST_SECTOR = "Primaria";

    @BeforeEach
    void setUp() {
        request = new ReportRequestDto();
        request.setStartDate(LocalDate.of(2024, 1, 1));
        request.setEndDate(LocalDate.of(2024, 12, 31));
        request.setReportType(ReportType.PROCESS_STATUS);

        mockReportData = List.of(
                TestProvider.provideReportDataDto("123", "Juan", "Pérez", "PROCESS_STATUS"),
                TestProvider.provideReportDataDto("456", "Ana", "Gómez", "PROCESS_STATUS")
        );
    }

    @Test
    void getExamAttendance_shouldReturnAttendanceReport() {
        // Arrange
        List<EnrollmentAttendanceDTO> expected = List.of(
                new EnrollmentAttendanceDTO(LocalDate.of(2025, 1, 15),"FIRST", "Primaria", 50, 10),
                new EnrollmentAttendanceDTO(LocalDate.of(2025, 1, 15),"SECOND", "Primaria", 60, 5)
        );

        when(reportsService.getEnrollmentAttendanceStats(
                LocalDate.parse(TEST_START_DATE),
                LocalDate.parse(TEST_END_DATE),
                Arrays.asList("FIRST", "SECOND"),
                TEST_SECTOR))
                .thenReturn(expected);

        // Act
        ResponseEntity<List<EnrollmentAttendanceDTO>> response = reportController.getExamAttendance(
                TEST_START_DATE, TEST_END_DATE, TEST_GRADE, TEST_SECTOR);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
        verify(reportsService).getEnrollmentAttendanceStats(
                LocalDate.parse(TEST_START_DATE),
                LocalDate.parse(TEST_END_DATE),
                Arrays.asList("FIRST", "SECOND"),
                TEST_SECTOR);
    }

    @Test
    void getExamAttendance_withDefaultValues_shouldUseDefaults() {
        // Arrange
        List<EnrollmentAttendanceDTO> expected = List.of(
                new EnrollmentAttendanceDTO(LocalDate.of(2025, 1, 15), "ALL", "ALL", 100, 20)
        );

        // Use any() matcher or match the actual behavior of parseGrades
        when(reportsService.getEnrollmentAttendanceStats(
                isNull(),
                isNull(),
                anyList(),
                eq("All")))
                .thenReturn(expected);

        // Act
        ResponseEntity<List<EnrollmentAttendanceDTO>> response = reportController.getExamAttendance(
                null, null, "All", "All");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
    }

    @Test
    void getExamSourceStatistics_shouldReturnSourceStatistics() {
        // Arrange
        List<ExamSourceDTO> expected = List.of(
                new ExamSourceDTO("Online", 150),
                new ExamSourceDTO("In-Person", 50)
        );

        when(reportsService.getExamSourceStatistics(
                LocalDate.parse(TEST_START_DATE),
                LocalDate.parse(TEST_END_DATE),
                Arrays.asList("FIRST", "SECOND"),
                TEST_SECTOR.trim()))
                .thenReturn(expected);

        // Act
        ResponseEntity<List<ExamSourceDTO>> response = reportController.getExamSourceStatistics(
                TEST_START_DATE, TEST_END_DATE, TEST_GRADE, TEST_SECTOR);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
    }

    @Test
    void getAdmissionFinal_shouldReturnAdmissionStats() {
        // Arrange
        List<AdmissionFinalDTO> expected = List.of(
                new AdmissionFinalDTO(LocalDate.parse("2025-01-15"), "FIRST", "Primaria", 100, 20),
                new AdmissionFinalDTO(LocalDate.parse("2025-01-16"), "SECOND", "Primaria", 80, 15)
        );

        when(reportsService.getAdmissionFinalStats(
                LocalDate.parse(TEST_START_DATE),
                LocalDate.parse(TEST_END_DATE),
                Arrays.asList("FIRST", "SECOND"),
                TEST_SECTOR.trim()))
                .thenReturn(expected);

        // Act
        ResponseEntity<List<AdmissionFinalDTO>> response = reportController.getAdmissionFinal(
                TEST_START_DATE, TEST_END_DATE, TEST_GRADE, TEST_SECTOR);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
    }

    @Test
    void getAcademicReport_shouldReturnAcademicReport() {
        // Arrange
        List<AcademicDistributionDTO> distribution = List.of(
                new AcademicDistributionDTO("A", BigDecimal.valueOf(10)),
                new AcademicDistributionDTO("B", BigDecimal.valueOf(20))
        );
        List<AcademicGradeAverageDTO> gradeAverages = List.of(
                new AcademicGradeAverageDTO("FIRST", BigDecimal.valueOf(85.5)),
                new AcademicGradeAverageDTO("SECOND", BigDecimal.valueOf(78.5))
        );
        AcademicExamReportDTO expected = new AcademicExamReportDTO(distribution, gradeAverages);

        when(reportsService.getAcademicExamReport(
                LocalDate.parse(TEST_START_DATE),
                LocalDate.parse(TEST_END_DATE),
                Arrays.asList("FIRST", "SECOND"),
                TEST_SECTOR.trim()))
                .thenReturn(expected);

        // Act
        ResponseEntity<AcademicExamReportDTO> response = reportController.getAcademicReport(
                TEST_START_DATE, TEST_END_DATE, TEST_GRADE, TEST_SECTOR);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expected, response.getBody());
    }


    @Test
    void generatePdfReport_withStatusFilter_shouldReturnFilteredPdf() throws Exception {
        // Arrange
        request.setStatusFilter(ProcessStatus.PENDING);
        byte[] mockPdfBytes = "PDF_CONTENT".getBytes();

        when(reportsService.generateReportData(request)).thenReturn(mockReportData);
        when(pdfReportGenerator.generateReport(mockReportData, request)).thenReturn(mockPdfBytes);

        // Act
        ResponseEntity<byte[]> response = reportController.generatePdfReport(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_PDF, response.getHeaders().getContentType());
        assertEquals("attachment; filename=report.pdf",
                response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        assertArrayEquals(mockPdfBytes, response.getBody());

        verify(reportsService).generateReportData(request);
        verify(pdfReportGenerator).generateReport(mockReportData, request);
    }

    @Test
    void generatePdfReport_withoutStatusFilter_shouldReturnAllStatuses() throws Exception {
        // Arrange
        request.setStatusFilter(null); // Explícitamente null
        byte[] mockPdfBytes = "PDF_CONTENT_ALL".getBytes();

        when(reportsService.generateReportData(request)).thenReturn(mockReportData);
        when(pdfReportGenerator.generateReport(mockReportData, request)).thenReturn(mockPdfBytes);

        // Act
        ResponseEntity<byte[]> response = reportController.generatePdfReport(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertArrayEquals(mockPdfBytes, response.getBody());


        ArgumentCaptor<ReportRequestDto> captor = ArgumentCaptor.forClass(ReportRequestDto.class);
        verify(reportsService).generateReportData(captor.capture());
        assertNull(captor.getValue().getStatusFilter());
    }

    @Test
    void generateCsvReport_withStatusFilter_shouldReturnFilteredCsv() throws Exception {
        // Arrange
        request.setStatusFilter(ProcessStatus.ACCEPTED);
        byte[] mockCsvBytes = "CSV_CONTENT".getBytes();

        when(reportsService.generateReportData(request)).thenReturn(mockReportData);
        when(csvReportGenerator.generateReport(mockReportData, request)).thenReturn(mockCsvBytes);

        // Act
        ResponseEntity<byte[]> response = reportController.generateCsvReport(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.parseMediaType("text/csv; charset=UTF-8"),
                response.getHeaders().getContentType());
        assertEquals("attachment; filename=report.csv",
                response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        assertArrayEquals(mockCsvBytes, response.getBody());
    }


    @Test
    void generateCsvReport_withoutStatusFilter_shouldReturnAllStatuses() throws Exception {
        // Arrange
        // statusFilter queda como null por defecto
        byte[] mockCsvBytes = "CSV_CONTENT_ALL".getBytes();

        when(reportsService.generateReportData(request)).thenReturn(mockReportData);
        when(csvReportGenerator.generateReport(mockReportData, request)).thenReturn(mockCsvBytes);

        // Act
        ResponseEntity<byte[]> response = reportController.generateCsvReport(request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());


        verify(csvReportGenerator).generateReport(mockReportData, request);
    }

    @Test
    void generateReports_withEmptyData_shouldReturnEmptyFile() throws Exception {
        // Arrange
        request.setStatusFilter(ProcessStatus.ELIGIBLE);
        List<ReportDataDto> emptyData = Collections.emptyList();
        byte[] emptyBytes = new byte[0];

        when(reportsService.generateReportData(request)).thenReturn(emptyData);
        when(pdfReportGenerator.generateReport(emptyData, request)).thenReturn(emptyBytes);
        when(csvReportGenerator.generateReport(emptyData, request)).thenReturn(emptyBytes);

        // Act - PDF
        ResponseEntity<byte[]> pdfResponse = reportController.generatePdfReport(request);

        // Assert - PDF
        assertNotNull(pdfResponse);
        assertArrayEquals(emptyBytes, pdfResponse.getBody());

        // Act - CSV
        ResponseEntity<byte[]> csvResponse = reportController.generateCsvReport(request);

        // Assert - CSV
        assertNotNull(csvResponse);
        assertArrayEquals(emptyBytes, csvResponse.getBody());
    }

    @Test
    void generateReports_withNonProcessStatusType_shouldIgnoreStatusFilter() throws Exception {
        // Arrange
        request.setReportType(ReportType.GRADE_TO_ENROLL);


        ArgumentCaptor<ReportRequestDto> captor = ArgumentCaptor.forClass(ReportRequestDto.class);

        // Act - PDF
        reportController.generatePdfReport(request);

        // Assert
        verify(reportsService).generateReportData(captor.capture());
        assertEquals(ReportType.GRADE_TO_ENROLL, captor.getValue().getReportType());
        assertNull(captor.getValue().getStatusFilter());
    }

}
