package cr.co.ctpcit.citsacbackend.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiQuestionsDto;
import org.junit.jupiter.api.Test;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class QuestionsDaiControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllExamQuestions() throws Exception {
        mockMvc.perform(get("/api/Dai"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetExamQuestionById() throws Exception {
        int testId = 1;
        mockMvc.perform(get("/api/Dai/{id}", testId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(testId));
    }

    @Test
    public void testDeleteExamQuestion() throws Exception {
        int testId = 1;
        mockMvc.perform(delete("/api/Dai/{id}", testId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdateExamQuestion() throws Exception {
        int testId = 1;
        DaiQuestionsDto preguntaDto = new DaiQuestionsDto(testId, "Describe una situación en la que te hayas sentido feliz.", Grades.TENTH, null);

        mockMvc.perform(put("/api/Dai/{id}", testId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(preguntaDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questionText").value("Describe una situación en la que te hayas sentido feliz."));
    }

    @Test
    public void testGetExamQuestionsByQuestionText() throws Exception {
        String searchText = "Como";
        mockMvc.perform(get("/api/Dai/search")
                        .param("questionText", searchText))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].questionText").value(org.hamcrest.Matchers.containsString(searchText)));
    }
}
