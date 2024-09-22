package cr.co.ctpcit.citsacbackend.rest;

import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiExamQuestionsDto;
import cr.co.ctpcit.citsacbackend.logic.services.DaiExamQuestionsService;
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

@WebMvcTest(DaiExamQuestionsController.class)
public class DaiExamQuestionsControllerUnitTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private DaiExamQuestionsService service;

  private DaiExamQuestionsDto question1;
  private DaiExamQuestionsDto question2;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    question1 = new DaiExamQuestionsDto(1, 101, "Me gusta explorar la naturaleza");
    question2 = new DaiExamQuestionsDto(1, 102, "Soy muy tímido");
  }

  @Test
  public void testGetExamQuestions() throws Exception {
    int examId = 1;
    List<DaiExamQuestionsDto> questions = Arrays.asList(question1, question2);
    when(service.getExamAnswers(examId)).thenReturn(questions);

    mockMvc.perform(
            get("/api/exams/{examId}/questions", examId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$[0].examId").value(question1.examId()))
        .andExpect(jsonPath("$[0].questionId").value(question1.questionId()))
        .andExpect(jsonPath("$[0].studentAnswer").value(question1.studentAnswer()))
        .andExpect(jsonPath("$[1].examId").value(question2.examId()))
        .andExpect(jsonPath("$[1].questionId").value(question2.questionId()))
        .andExpect(jsonPath("$[1].studentAnswer").value(question2.studentAnswer()));
  }
}
