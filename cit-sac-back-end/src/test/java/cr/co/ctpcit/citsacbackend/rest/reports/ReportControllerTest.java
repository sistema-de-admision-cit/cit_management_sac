package cr.co.ctpcit.citsacbackend.rest.reports;

import cr.co.ctpcit.citsacbackend.TestProvider;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.data.enums.ReportType;
import cr.co.ctpcit.citsacbackend.data.utils.CsvReportGenerator;
import cr.co.ctpcit.citsacbackend.data.utils.PdfReportGenerator;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.ReportDataDto;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.ReportRequestDto;
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

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        assertEquals(MediaType.TEXT_PLAIN, response.getHeaders().getContentType());
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
