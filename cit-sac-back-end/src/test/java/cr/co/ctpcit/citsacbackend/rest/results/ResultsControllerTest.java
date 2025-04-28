package cr.co.ctpcit.citsacbackend.rest.results;

import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.logic.dto.results.ResultDTO;
import cr.co.ctpcit.citsacbackend.data.enums.EnglishLevel;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.data.enums.Recommendation;
import cr.co.ctpcit.citsacbackend.logic.dto.results.StudentResultsDetailsDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.results.UpdateStatusDTO;
import cr.co.ctpcit.citsacbackend.logic.services.results.ResultsServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResultsControllerTest {

    @Mock
    private ResultsServiceImpl resultsService;

    @InjectMocks
    private ResultsController resultsController;

    private ResultDTO resultDTO;
    private StudentResultsDetailsDTO detailsDTO;
    private UpdateStatusDTO updateStatusDTO;

    @BeforeEach
    void setUp() {
        resultDTO = new ResultDTO(
                "123456789",
                "John",
                "Doe",
                null,
                LocalDate.now(),
                Grades.FIFTH,
                new BigDecimal("85.50"),
                ProcessStatus.ELIGIBLE
        );

        detailsDTO = new StudentResultsDetailsDTO(
                "John Doe",
                "123456789",
                Grades.FIFTH,
                new BigDecimal("80.00"),
                EnglishLevel.B2,
                new BigDecimal("90.00"),
                new BigDecimal("85.50"),
                Recommendation.ADMIT,
                "Excellent performance",
                ProcessStatus.ELIGIBLE
        );

        updateStatusDTO = new UpdateStatusDTO();
        updateStatusDTO.setNewStatus(ProcessStatus.ACCEPTED);
    }

    @Test
    void getExamResults_shouldReturnPaginatedResults() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 25);
        List<ResultDTO> resultList = Arrays.asList(resultDTO);
        Page<ResultDTO> expectedPage = new PageImpl<>(resultList, pageable, 1);

        when(resultsService.getExamResults(any(Pageable.class))).thenReturn(expectedPage);
        // Act
        ResponseEntity<List<ResultDTO>> response =
                resultsController.getExamResults(pageable);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(resultDTO, response.getBody().get(0));

        verify(resultsService, times(1)).getExamResults(pageable);
    }

    @Test
    void getExamResults_withEmptyPage_shouldReturnEmptyList() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 25);
        Page<ResultDTO> emptyPage = new PageImpl<>(List.of(), pageable, 0);

        when(resultsService.getExamResults(any(Pageable.class)))
                .thenReturn(emptyPage);

        // Act
        ResponseEntity<List<ResultDTO>> response =
                resultsController.getExamResults(pageable);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void searchExamResults_withValidQuery_shouldReturnResults() {
        // Arrange
        String query = "John";
        Pageable pageable = PageRequest.of(0, 25);
        List<ResultDTO> resultList = Arrays.asList(resultDTO);
        Page<ResultDTO> expectedPage = new PageImpl<>(resultList, pageable, 1);

        when(resultsService.searchResults(eq(query), any(Pageable.class)))
                .thenReturn(expectedPage);

        // Act
        ResponseEntity<List<ResultDTO>> response =
                resultsController.searchExamResults(query, pageable);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(resultDTO, response.getBody().get(0));
    }

    @Test
    void searchExamResults_withEmptyQuery_shouldReturnAllResults() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 25);
        List<ResultDTO> resultList = Arrays.asList(resultDTO);
        Page<ResultDTO> expectedPage = new PageImpl<>(resultList, pageable, 1);

        when(resultsService.getExamResults(any(Pageable.class)))
                .thenReturn(expectedPage);

        // Act
        ResponseEntity<List<ResultDTO>> response =
                resultsController.searchExamResults("", pageable);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());

        verify(resultsService, times(1)).getExamResults(pageable);
        verify(resultsService, never()).searchResults(any(), any());
    }

    @Test
    void getStudentDetails_withValidIdNumber_shouldReturnDetails() {
        // Arrange
        String idNumber = "123456789";

        when(resultsService.getStudentExamDetails(idNumber))
                .thenReturn(detailsDTO);

        // Act
        ResponseEntity<StudentResultsDetailsDTO> response =
                resultsController.getStudentDetails(idNumber);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(detailsDTO, response.getBody());
    }

    @Test
    void getStudentDetails_withInvalidIdNumber_shouldThrowException() {
        // Arrange
        String invalidId = "000000000";
        String errorMessage = "Student not found";

        when(resultsService.getStudentExamDetails(invalidId))
                .thenThrow(new RuntimeException(errorMessage));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            resultsController.getStudentDetails(invalidId);
        });

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void updateStudentStatus_withValidData_shouldReturnOk() {
        // Arrange
        String idNumber = "123456789";

        doNothing().when(resultsService)
                .updateEnrollmentStatus(eq(idNumber), any(UpdateStatusDTO.class));

        // Act
        ResponseEntity<Void> response =
                resultsController.updateStudentStatus(idNumber, updateStatusDTO);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        verify(resultsService, times(1))
                .updateEnrollmentStatus(idNumber, updateStatusDTO);
    }

    @Test
    void updateStudentStatus_withInvalidStatus_shouldThrowException() {
        // Arrange
        String idNumber = "123456789";
        UpdateStatusDTO invalidStatus = new UpdateStatusDTO();
        invalidStatus.setNewStatus(ProcessStatus.PENDING);
        String errorMessage = "Only ACCEPTED or REJECTED status allowed";

        doThrow(new RuntimeException(errorMessage))
                .when(resultsService)
                .updateEnrollmentStatus(eq(idNumber), eq(invalidStatus));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            resultsController.updateStudentStatus(idNumber, invalidStatus);
        });

        assertEquals(errorMessage, exception.getMessage());
    }


}
