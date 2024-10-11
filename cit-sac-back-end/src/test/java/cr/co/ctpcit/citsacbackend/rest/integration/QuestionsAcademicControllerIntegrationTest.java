package cr.co.ctpcit.citsacbackend.rest.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.AuthResponseDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.AcademicQuestionsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class QuestionsAcademicControllerIntegrationTest {

  AuthResponseDto authResponseDto;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() throws Exception {
    // @formatter:off
    MvcResult result = this.mockMvc.perform(post("/api/auth/login")
            .with(httpBasic("sysadmin@cit.co.cr", "campus12")))
        .andExpect(status().isOk())
        .andReturn();

    authResponseDto = objectMapper.readValue(result.getResponse().getContentAsString(), AuthResponseDto.class);
  }

  @Test
  public void testGetAllExamQuestions() throws Exception {
    mockMvc.perform(get("/api/questions-academic")
            .header("Authorization", "Bearer " + authResponseDto.token()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  public void testGetExamQuestionById() throws Exception {
    int testId = 1;
    mockMvc.perform(get("/api/questions-academic/{id}", testId)
            .header("Authorization", "Bearer " + authResponseDto.token()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(testId));
  }

  @Test
  public void testDeleteExamQuestion() throws Exception {
    int testId = 1;
    mockMvc.perform(delete("/api/questions-academic/{id}", testId)
            .header("Authorization", "Bearer " + authResponseDto.token()))
        .andExpect(status().isNoContent());
  }

  @Test
  public void testUpdateExamQuestion() throws Exception {
    int testId = 1;
    AcademicQuestionsDto preguntaDto =
        new AcademicQuestionsDto(testId, "Al resolver la ecuación: 2x+1=5, obtenemos",
            Grades.SEVENTH, "3", "2", "1", "5", "B", null);

    mockMvc.perform(
            put("/api/questions-academic/{id}", testId)
                .header("Authorization", "Bearer " + authResponseDto.token())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(preguntaDto))).andExpect(status().isOk())
        .andExpect(jsonPath("$.questionText").value("Al resolver la ecuación: 2x+1=5, obtenemos"));
  }

  @Test
  public void testGetExamQuestionsByQuestionText() throws Exception {
    String searchText = "Cual";
    mockMvc.perform(get("/api/questions-academic/search").param("questionText", searchText)
            .header("Authorization", "Bearer " + authResponseDto.token()))
        .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray()).andExpect(
            jsonPath("$[0].questionText").value(org.hamcrest.Matchers.containsString(searchText)));
  }
}
