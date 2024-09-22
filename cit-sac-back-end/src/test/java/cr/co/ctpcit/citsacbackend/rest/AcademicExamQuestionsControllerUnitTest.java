package cr.co.ctpcit.citsacbackend.rest;

import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.AcademicExamQuestionsDto;
import cr.co.ctpcit.citsacbackend.logic.services.AcademicExamQuestionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AcademicExamQuestionsController.class)
public class AcademicExamQuestionsControllerUnitTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AcademicExamQuestionsService service;

  private AcademicExamQuestionsDto question1;
  private AcademicExamQuestionsDto question2;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    question1 = new AcademicExamQuestionsDto(1, 101, "El verbo ir");
    question2 = new AcademicExamQuestionsDto(1, 102, "La respuesta es 7");
  }

  @Test
  public void testGetAcademicExamQuestions() throws Exception {
    int examId = 1;
    List<AcademicExamQuestionsDto> questions = Arrays.asList(question1, question2);
    when(service.getExamAnswers(examId)).thenReturn(questions);

    mockMvc.perform(get("/api/academic-exams/{examId}/questions", examId).contentType(
            MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$[0].examId").value(question1.examId()))
        .andExpect(jsonPath("$[0].questionId").value(question1.questionId()))
        .andExpect(jsonPath("$[0].studentAnswer").value(question1.studentAnswer()))
        .andExpect(jsonPath("$[1].examId").value(question2.examId()))
        .andExpect(jsonPath("$[1].questionId").value(question2.questionId()))
        .andExpect(jsonPath("$[1].studentAnswer").value(question2.studentAnswer()));
  }
}
