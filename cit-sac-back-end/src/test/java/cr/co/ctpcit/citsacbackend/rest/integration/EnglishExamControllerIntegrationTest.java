package cr.co.ctpcit.citsacbackend.rest.integration;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import cr.co.ctpcit.citsacbackend.data.entities.exams.english.EnglishExamEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.english.EnglishScoreEntryDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.logs.englishExams.EnglishExamLogDto;
import cr.co.ctpcit.citsacbackend.logic.services.exams.english.EnglishExamServiceImplementation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class EnglishExamControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnglishExamServiceImplementation englishExamService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetEnglishExamsHealth() throws Exception {
        mockMvc.perform(get("/api/exams/english/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("English exams are healthy!"));
    }

    @Test
    public void testGetEnglishExamScores() throws Exception {
        List<EnglishExamEntity> mockExams = new ArrayList<>();
        when(englishExamService.getAll()).thenReturn(mockExams);

        mockMvc.perform(get("/api/exams/english/get-exams"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testUploadEnglishScores_Success() throws Exception {
        List<EnglishScoreEntryDTO> scores = Arrays.asList(
                new EnglishScoreEntryDTO(1L, "Juan Carlos", "Pérez Sánchez", "2024-09-11", "B2", "85%"),
                new EnglishScoreEntryDTO(2L, "María Gómez", "López", "2024-09-11", "C1", "90%")
        );

        List<EnglishExamLogDto> expectedLogs = Arrays.asList(
                new EnglishExamLogDto(1, 1001, 1, 75, 85, LocalDate.parse("2024-09-11"), "Processed", null),
                new EnglishExamLogDto(1, 1002, 2, 80, 90, LocalDate.parse("2024-09-11"), "Processed", null)
        );

        when(englishExamService.processEnglishScores(anyList())).thenReturn(expectedLogs);

        mockMvc.perform(post("/api/exams/english/update-scores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scores)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].processId").value(1))
                .andExpect(jsonPath("$[0].enrollmentId").value(1001))
                .andExpect(jsonPath("$[0].trackTestExamId").value(1))
                .andExpect(jsonPath("$[0].previousScore").value(75))
                .andExpect(jsonPath("$[0].newScore").value(85))
                .andExpect(jsonPath("$[0].examDate").value("2024-09-11"))
                .andExpect(jsonPath("$[0].status").value("Processed"))
                .andExpect(jsonPath("$[1].processId").value(1))
                .andExpect(jsonPath("$[1].enrollmentId").value(1002))
                .andExpect(jsonPath("$[1].trackTestExamId").value(2))
                .andExpect(jsonPath("$[1].previousScore").value(80))
                .andExpect(jsonPath("$[1].newScore").value(90))
                .andExpect(jsonPath("$[1].examDate").value("2024-09-11"))
                .andExpect(jsonPath("$[1].status").value("Processed"));
    }

    @Test
    public void testUploadEnglishScoresEmptyLogs() throws Exception {
        String scoresJson = "[]";
        mockMvc.perform(post("/api/exams/english/update-scores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scoresJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("[]"));
    }
}
