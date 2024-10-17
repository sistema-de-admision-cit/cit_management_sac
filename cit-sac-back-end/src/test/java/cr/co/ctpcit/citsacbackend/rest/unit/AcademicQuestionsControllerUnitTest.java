package cr.co.ctpcit.citsacbackend.rest.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
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
public class AcademicQuestionsControllerUnitTest {

  AuthResponseDto authResponseDto;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private AcademicQuestionsServiceImplementation academicQuestionsService;
  @MockBean
  private FileSystemStorageService serviceStorage;
  private AcademicQuestionsDto question1;
  private AcademicQuestionsDto question2;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
    question1 =
        new AcademicQuestionsDto(1, "¿Cuál es el río más largo del mundo?", Grades.SEVENTH, "Nilo",
            "Amazonas", "Yangtsé", "Misisipi", "B", null);
    question2 = new AcademicQuestionsDto(2, "¿Quién escribió La Odisea?", Grades.FIFTH, "Homero",
        "Virgilio", "Dante", "Shakespeare", "A", null);

    MvcResult result = this.mockMvc.perform(
            post("/api/auth/login").with(httpBasic("sysadmin@cit.co.cr", "campus12")))
        .andExpect(status().isOk()).andReturn();

    authResponseDto =
        objectMapper.readValue(result.getResponse().getContentAsString(), AuthResponseDto.class);
  }

  @Test
  void getAllExamQuestions() throws Exception {
    List<AcademicQuestionsDto> allQuestions = Arrays.asList(question1, question2);
    when(academicQuestionsService.obtenerTodasLasPreguntas()).thenReturn(allQuestions);

    mockMvc.perform(
            get("/api/questions-academic").header("Authorization", "Bearer " + authResponseDto.token())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(question1.id()))
        .andExpect(jsonPath("$[1].id").value(question2.id()));
  }

  @Test
  void getExamQuestionById() throws Exception {
    when(academicQuestionsService.obtenerPreguntaPorId(1)).thenReturn(question1);

    mockMvc.perform(get("/api/questions-academic/1").header("Authorization",
            "Bearer " + authResponseDto.token()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(question1.id()));
  }

  @Test
  void getExamQuestionByIdNotFound() throws Exception {
    when(academicQuestionsService.obtenerPreguntaPorId(1)).thenThrow(
        new NoSuchElementException("Pregunta no encontrada con el id 1"));

    mockMvc.perform(get("/api/questions-academic/1").header("Authorization",
            "Bearer " + authResponseDto.token()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Pregunta no encontrada con el id 1"));
  }

  @Test
  void deleteExamQuestion() throws Exception {
    mockMvc.perform(delete("/api/questions-academic/1").header("Authorization",
            "Bearer " + authResponseDto.token()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  void deleteExamQuestionNotFound() throws Exception {
    doThrow(new NoSuchElementException("Pregunta no encontrada con el id 1")).when(
        academicQuestionsService).eliminarPregunta(1);

    mockMvc.perform(delete("/api/questions-academic/1").header("Authorization",
            "Bearer " + authResponseDto.token()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Pregunta no encontrada con el id 1"));
  }

  @Test
  void updateExamQuestion() throws Exception {
    AcademicQuestionsDto updatedQuestion =
        new AcademicQuestionsDto(1, "¿Cuál es el océano más grande del mundo?", Grades.FORTH,
            "Atlántico", "Pacífico", "Índico", "Ártico", "B", null);
    when(academicQuestionsService.modificarPregunta(1, updatedQuestion)).thenReturn(
        updatedQuestion);

    mockMvc.perform(put("/api/questions-academic/1").header("Authorization",
            "Bearer " + authResponseDto.token()).contentType(MediaType.APPLICATION_JSON).content(
            "{\"id\":1,\"questionText\":\"¿Cuál es el océano más grande del mundo?\",\"questionGrade\":\"FORTH\",\"option_A\":\"Atlántico\",\"option_B\":\"Pacífico\",\"option_C\":\"Índico\",\"option_D\":\"Ártico\",\"correctOption\":\"B\",\"imageUrl\":null}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.questionText").value("¿Cuál es el océano más grande del mundo?"))
        .andExpect(jsonPath("$.questionGrade").value("FORTH"))
        .andExpect(jsonPath("$.option_A").value("Atlántico"))
        .andExpect(jsonPath("$.option_B").value("Pacífico"))
        .andExpect(jsonPath("$.option_C").value("Índico"))
        .andExpect(jsonPath("$.option_D").value("Ártico"))
        .andExpect(jsonPath("$.correctOption").value("B"))
        .andExpect(jsonPath("$.imageUrl").isEmpty());
  }

  @Test
  void updateExamQuestionNotFound() throws Exception {
    AcademicQuestionsDto updatedQuestion =
        new AcademicQuestionsDto(1, "¿Cuál es el océano más grande del mundo?", Grades.FORTH,
            "Atlántico", "Pacífico", "Índico", "Ártico", "B", null);
    when(academicQuestionsService.modificarPregunta(1, updatedQuestion)).thenThrow(
        new NoSuchElementException("Pregunta no encontrada con el id 1"));

    mockMvc.perform(put("/api/questions-academic/1").header("Authorization",
            "Bearer " + authResponseDto.token()).contentType(MediaType.APPLICATION_JSON).content(
            "{\"id\":1,\"questionText\":\"¿Cuál es el océano más grande del mundo?\",\"questionGrade\":\"FORTH\",\"option_A\":\"Atlántico\",\"option_B\":\"Pacífico\",\"option_C\":\"Índico\",\"option_D\":\"Ártico\",\"correctOption\":\"B\",\"imageUrl\":null}"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Pregunta no encontrada con el id 1"));
  }

  @Test
  void getExamQuestionsByQuestionText() throws Exception {
    List<AcademicQuestionsDto> questions = Arrays.asList(question1);
    when(academicQuestionsService.obtenerPreguntasPorQuestionText("cual")).thenReturn(questions);

    mockMvc.perform(get("/api/questions-academic/search").param("questionText", "cual")
            .header("Authorization", "Bearer " + authResponseDto.token())
            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(question1.id()))
        .andExpect(jsonPath("$[0].questionText").value(question1.questionText()));
  }
}

