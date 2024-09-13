package cr.co.ctpcit.citsacbackend.rest;

import cr.co.ctpcit.citsacbackend.data.entities.exams.academic.AcademicQuestionsEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.repositories.AcademicQuestionsRepository;
import cr.co.ctpcit.citsacbackend.logic.services.examsImplementations.AcademicQuestionsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ExamAcademicControllerUnitTest {

    @Mock
    private AcademicQuestionsRepository academicQuestionsRepository;

    @InjectMocks
    private AcademicQuestionsService academicQuestionsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerTodasLasPreguntas() {
        // Arrange
        AcademicQuestionsEntity pregunta1 = new AcademicQuestionsEntity();
        pregunta1.setId(1);
        pregunta1.setQuestionText("¿Cuál es la capital de Francia?");

        AcademicQuestionsEntity pregunta2 = new AcademicQuestionsEntity();
        pregunta2.setId(2);
        pregunta2.setQuestionText("¿Cuánto es 2 + 2?");

        List<AcademicQuestionsEntity> preguntas = Arrays.asList(pregunta1, pregunta2);

        when(academicQuestionsRepository.findAll()).thenReturn(preguntas);

        // Act
        List<AcademicQuestionsEntity> result = academicQuestionsService.obtenerTodasLasPreguntas();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("¿Cuál es la capital de Francia?", result.get(0).getQuestionText());
        assertEquals("¿Cuánto es 2 + 2?", result.get(1).getQuestionText());
        verify(academicQuestionsRepository).findAll();
    }

    @Test
    public void testObtenerTodasLasPreguntas_Error() {
        // Arrange
        when(academicQuestionsRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                academicQuestionsService.obtenerTodasLasPreguntas());
        assertEquals("Error al obtener las preguntas", thrown.getMessage());
    }

    @Test
    public void testEliminarPregunta_Success() {
        // Arrange
        Integer id = 1;
        AcademicQuestionsEntity pregunta = new AcademicQuestionsEntity();
        pregunta.setId(id);

        when(academicQuestionsRepository.findById(id)).thenReturn(Optional.of(pregunta));

        // Act
        academicQuestionsService.eliminarPregunta(id);

        // Assert
        verify(academicQuestionsRepository).findById(id);
        verify(academicQuestionsRepository).delete(pregunta);
    }

    @Test
    public void testEliminarPregunta_NotFound() {
        // Arrange
        Integer id = 1;
        when(academicQuestionsRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () ->
                academicQuestionsService.eliminarPregunta(id));
        assertEquals("Pregunta no encontrada con el id " + id, thrown.getMessage());
    }

    @Test
    public void testModificarPregunta_Success() {
        // Arrange
        Integer id = 1;
        AcademicQuestionsEntity preguntaExistente = new AcademicQuestionsEntity();
        preguntaExistente.setId(id);
        preguntaExistente.setQuestionText("Pregunta original");
        preguntaExistente.setQuestionGrade(Grades.FIFTH);

        AcademicQuestionsEntity preguntaModificada = new AcademicQuestionsEntity();
        preguntaModificada.setId(id);
        preguntaModificada.setQuestionText("¿Cuál es la capital de Italia?");
        preguntaModificada.setQuestionGrade(Grades.FIRST);

        when(academicQuestionsRepository.findById(id)).thenReturn(Optional.of(preguntaExistente));
        when(academicQuestionsRepository.save(any(AcademicQuestionsEntity.class))).thenReturn(preguntaModificada);

        // Act
        AcademicQuestionsEntity result = academicQuestionsService.modificarPregunta(id, "¿Cuál es la capital de Italia?", Grades.FIRST, null, null, null, null, null, null);

        // Assert
        assertNotNull(result);
        assertEquals("¿Cuál es la capital de Italia?", result.getQuestionText());
        assertEquals(Grades.FIRST, result.getQuestionGrade());
        verify(academicQuestionsRepository).findById(id);
        verify(academicQuestionsRepository).save(any(AcademicQuestionsEntity.class));
    }

    @Test
    public void testModificarPregunta_PreguntaNoEncontrada() {
        // Arrange
        Integer id = 1;
        when(academicQuestionsRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () ->
                academicQuestionsService.modificarPregunta(id, "Nueva pregunta", Grades.FIRST, null, null, null, null, null, null));
        assertEquals("Pregunta no encontrada con el id " + id, thrown.getMessage());
    }

    @Test
    public void testModificarPregunta_ErrorEnRepository() {
        // Arrange
        Integer id = 1;
        AcademicQuestionsEntity preguntaExistente = new AcademicQuestionsEntity();
        preguntaExistente.setId(id);

        when(academicQuestionsRepository.findById(id)).thenReturn(Optional.of(preguntaExistente));
        when(academicQuestionsRepository.save(any(AcademicQuestionsEntity.class))).thenThrow(new RuntimeException("Error al guardar"));

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                academicQuestionsService.modificarPregunta(id, "Pregunta modificada", Grades.FIRST, null, null, null, null, null, null));
        assertEquals("Error al guardar", thrown.getMessage());
    }

}
