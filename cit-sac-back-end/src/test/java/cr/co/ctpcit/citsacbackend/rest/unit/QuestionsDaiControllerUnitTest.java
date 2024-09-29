package cr.co.ctpcit.citsacbackend.rest.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.AuthResponseDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiQuestionsDto;
import cr.co.ctpcit.citsacbackend.logic.services.examsImplementations.DaiQuestionsServiceImplementation;
import cr.co.ctpcit.citsacbackend.logic.services.storage.FileSystemStorageService;
import cr.co.ctpcit.citsacbackend.rest.exams.dai.QuestionsDaiController;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@Rollback
public class QuestionsDaiControllerUnitTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private DaiQuestionsServiceImplementation daiQuestionsService;

  @MockBean
  private FileSystemStorageService serviceStorage;

  private DaiQuestionsDto question1;
  private DaiQuestionsDto question2;

  AuthResponseDto authResponseDto;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
    question1 = new DaiQuestionsDto(1,
        "¿Puedes contarme sobre una vez en la que enfrentaste una situación difícil en la escuela y cómo la manejaste?",
        Grades.SEVENTH, null);
    question2 = new DaiQuestionsDto(5,
        "¿Cómo sueles manejar el estrés o la presión, especialmente durante los exámenes o proyectos importantes?",
        Grades.FIFTH, "imagen.jpg");

    MvcResult result = this.mockMvc.perform(
            post("/api/auth/login").with(httpBasic("sysadmin@cit.co.cr", "campus12")))
        .andExpect(status().isOk()).andReturn();

    authResponseDto =
        objectMapper.readValue(result.getResponse().getContentAsString(), AuthResponseDto.class);
  }

  @Test
  void getAllExamQuestions() throws Exception {
    List<DaiQuestionsDto> allQuestions = Arrays.asList(question1, question2);
    when(daiQuestionsService.obtenerTodasLasPreguntas()).thenReturn(allQuestions);

    mockMvc.perform(
            get("/api/questions-dai").header("Authorization", "Bearer " + authResponseDto.token())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(question1.id()))
        .andExpect(jsonPath("$[1].id").value(question2.id()));
  }

  @Test
  void getExamQuestionById() throws Exception {
    when(daiQuestionsService.obtenerPreguntaPorId(1)).thenReturn(question1);

    mockMvc.perform(
            get("/api/questions-dai/1").header("Authorization", "Bearer " + authResponseDto.token())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(question1.id()));
  }

  @Test
  void getExamQuestionByIdNotFound() throws Exception {
    when(daiQuestionsService.obtenerPreguntaPorId(1)).thenThrow(
        new NoSuchElementException("Pregunta no encontrada con el id 1"));

    mockMvc.perform(
            get("/api/questions-dai/1").header("Authorization", "Bearer " + authResponseDto.token())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
        .andExpect(content().string("Pregunta no encontrada con el id 1"));
  }

  @Test
  void deleteExamQuestion() throws Exception {
    mockMvc.perform(
        delete("/api/questions-dai/1").header("Authorization", "Bearer " + authResponseDto.token())
            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
  }

  @Test
  void deleteExamQuestionNotFound() throws Exception {
    doThrow(new NoSuchElementException("Pregunta no encontrada con el id 1")).when(
        daiQuestionsService).eliminarPregunta(1);

    mockMvc.perform(
            delete("/api/questions-dai/1").header("Authorization", "Bearer " + authResponseDto.token())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
        .andExpect(content().string("Pregunta no encontrada con el id 1"));
  }

  @Test
  void updateExamQuestion() throws Exception {
    DaiQuestionsDto updatedQuestion =
        new DaiQuestionsDto(1, "¿Cómo te has sentido las últimas semanas?", Grades.FORTH, null);
    when(daiQuestionsService.modificarPregunta(1, updatedQuestion)).thenReturn(updatedQuestion);

    mockMvc.perform(
            put("/api/questions-dai/1").header("Authorization", "Bearer " + authResponseDto.token())
                .contentType(MediaType.APPLICATION_JSON).content(
                    "{\"id\":1,\"questionText\":\"¿Cómo te has sentido las últimas semanas?\",\"questionGrade\":\"FORTH\",\"imageUrl\":null}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.questionText").value("¿Cómo te has sentido las últimas semanas?"))
        .andExpect(jsonPath("$.questionGrade").value("FORTH"))
        .andExpect(jsonPath("$.imageUrl").isEmpty());
  }

  @Test
  void updateExamQuestionNotFound() throws Exception {
    DaiQuestionsDto updatedQuestion =
        new DaiQuestionsDto(1, "¿Cómo te has sentido las últimas semanas?", Grades.FORTH, null);
    when(daiQuestionsService.modificarPregunta(1, updatedQuestion)).thenThrow(
        new NoSuchElementException("Pregunta no encontrada con el id 1"));

    mockMvc.perform(
            put("/api/questions-dai/1").header("Authorization", "Bearer " + authResponseDto.token())
                .contentType(MediaType.APPLICATION_JSON).content(
                    "{\"id\":1,\"questionText\":\"¿Cómo te has sentido las últimas semanas?\",\"questionGrade\":\"FORTH\",\"imageUrl\":null}"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Pregunta no encontrada con el id 1"));
  }

  @Test
  void getExamQuestionsByQuestionText() throws Exception {
    List<DaiQuestionsDto> questions = Arrays.asList(question1);
    when(daiQuestionsService.obtenerPreguntasPorQuestionText("Puedes")).thenReturn(questions);

    mockMvc.perform(get("/api/questions-dai/search").param("questionText", "Puedes")
            .header("Authorization", "Bearer " + authResponseDto.token())
            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(question1.id()))
        .andExpect(jsonPath("$[0].questionText").value(question1.questionText()));
  }
}
