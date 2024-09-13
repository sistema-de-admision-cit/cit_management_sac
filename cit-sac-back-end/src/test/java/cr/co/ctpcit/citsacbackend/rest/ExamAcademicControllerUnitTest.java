package cr.co.ctpcit.citsacbackend.rest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.data.entities.exams.academic.AcademicQuestionsEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.logic.services.examsImplementations.AcademicQuestionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;


@SpringBootTest
@AutoConfigureMockMvc
public class ExamAcademicControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ExamAcademicController examAcademicController;

    @Mock
    private AcademicQuestionsService academicQuestionsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerPreguntas() {
        // Arrange
        List<AcademicQuestionsEntity> preguntas = Arrays.asList(new AcademicQuestionsEntity(), new AcademicQuestionsEntity());
        when(academicQuestionsService.obtenerTodasLasPreguntas()).thenReturn(preguntas);

        // Act
        ResponseEntity<List<AcademicQuestionsEntity>> response = examAcademicController.obtenerPreguntas();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(preguntas, response.getBody());
    }

    @Test
    public void testModificarPregunta() {
        // Arrange
        AcademicQuestionsEntity request = new AcademicQuestionsEntity();
        request.setQuestionText("¿Cuál es la capital de Costa Rica?");
        request.setQuestionGrade(Grades.TENTH);
        request.setImageUrl("http://example.com/image.png");
        request.setOptionA("San Jose");
        request.setOptionB("Heredia");
        request.setOptionC("Cartago");
        request.setOptionD("Limon");
        request.setCorrectOption("A");

        AcademicQuestionsEntity preguntaModificada = new AcademicQuestionsEntity();
        when(academicQuestionsService.modificarPregunta(anyInt(), anyString(), any(Grades.class), anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(preguntaModificada);

        // Act
        ResponseEntity<AcademicQuestionsEntity> response = examAcademicController.modificarPregunta(1, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(preguntaModificada, response.getBody());
    }

    @Test
    public void testModificarPregunta_NotFound() throws Exception {
        // Arrange
        AcademicQuestionsEntity request = new AcademicQuestionsEntity();
        when(academicQuestionsService.modificarPregunta(anyInt(), anyString(), any(Grades.class), anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenThrow(new NoSuchElementException("Pregunta no encontrada con el id 7"));

        // Act & Assert
        mockMvc.perform(put("/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testEliminarPregunta() {
        // Act
        ResponseEntity<Void> response = examAcademicController.eliminarPregunta(1);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(academicQuestionsService, times(1)).eliminarPregunta(1);
    }

    @Test
    public void testEliminarPregunta_NotFound() {
        // Arrange
        doThrow(new NoSuchElementException()).when(academicQuestionsService).eliminarPregunta(1);

        // Act
        ResponseEntity<Void> response = examAcademicController.eliminarPregunta(1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

