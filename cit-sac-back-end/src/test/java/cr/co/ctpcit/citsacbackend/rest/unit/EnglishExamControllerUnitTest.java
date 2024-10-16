package cr.co.ctpcit.citsacbackend.rest.unit;

import cr.co.ctpcit.citsacbackend.data.entities.exams.english.EnglishExamEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.english.EnglishScoreEntryDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.logs.englishExams.EnglishExamLogDto;
import cr.co.ctpcit.citsacbackend.logic.services.exams.english.EnglishExamServiceImplementation;
import cr.co.ctpcit.citsacbackend.rest.exams.english.EnglishExamController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EnglishExamControllerUnitTest {

    @Mock
    private EnglishExamServiceImplementation englishExamService;

    @InjectMocks
    private EnglishExamController englishExamController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetEnglishExamsHealth() {
        ResponseEntity<String> response = englishExamController.getEnglishExams();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("English exams are healthy!", response.getBody());
    }

    @Test
    public void testGetEnglishExamScores() {
        when(englishExamService.getAll()).thenReturn(new ArrayList<>());
        ResponseEntity<List<EnglishExamEntity>> response = englishExamController.getEnglishExamScores();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new ArrayList<>(), response.getBody());
    }


    @Test
    public void testUploadEnglishScoresFailure() {
        List<EnglishScoreEntryDTO> mockScores = new ArrayList<>();
        List<EnglishExamLogDto> emptyLogs = new ArrayList<>();

        when(englishExamService.processEnglishScores(mockScores)).thenReturn(emptyLogs);

        ResponseEntity<List<EnglishExamLogDto>> response = englishExamController.uploadEnglishScores(mockScores);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(emptyLogs, response.getBody());
    }
}
