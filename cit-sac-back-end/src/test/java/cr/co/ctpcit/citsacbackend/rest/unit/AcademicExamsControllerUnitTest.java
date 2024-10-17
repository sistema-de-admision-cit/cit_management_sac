package cr.co.ctpcit.citsacbackend.rest.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.AuthResponseDto;
import cr.co.ctpcit.citsacbackend.logic.services.storage.FileSystemStorageService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class AcademicExamsControllerUnitTest {

  AuthResponseDto authResponseDto;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private AcademicExamQuestionsService service;
  @MockBean
  private FileSystemStorageService serviceStorage;
  private AcademicExamQuestionsDto question1;
  private AcademicExamQuestionsDto question2;

  @BeforeEach
  public void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
    question1 = new AcademicExamQuestionsDto(1, 101, "El verbo ir");
    question2 = new AcademicExamQuestionsDto(1, 102, "La respuesta es 7");

    MvcResult result = this.mockMvc.perform(
            post("/api/auth/login").with(httpBasic("sysadmin@cit.co.cr", "campus12")))
        .andExpect(status().isOk()).andReturn();

    authResponseDto =
        objectMapper.readValue(result.getResponse().getContentAsString(), AuthResponseDto.class);
  }

  @Test
  public void testGetAcademicExamQuestions() throws Exception {
    int examId = 1;
    List<AcademicExamQuestionsDto> questions = Arrays.asList(question1, question2);
    when(service.getExamAnswers(examId)).thenReturn(questions);

    mockMvc.perform(
            get("/api/academic-exam-questions/{examId}/questions", examId).header("Authorization",
                "Bearer " + authResponseDto.token()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$[0].examId").value(question1.examId()))
        .andExpect(jsonPath("$[0].questionId").value(question1.questionId()))
        .andExpect(jsonPath("$[0].studentAnswer").value(question1.studentAnswer()))
        .andExpect(jsonPath("$[1].examId").value(question2.examId()))
        .andExpect(jsonPath("$[1].questionId").value(question2.questionId()))
        .andExpect(jsonPath("$[1].studentAnswer").value(question2.studentAnswer()));
  }
}
