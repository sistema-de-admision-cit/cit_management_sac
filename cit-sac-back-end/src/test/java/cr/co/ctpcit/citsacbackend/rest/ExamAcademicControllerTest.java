package cr.co.ctpcit.citsacbackend.rest;

import cr.co.ctpcit.citsacbackend.data.entities.exams.academic.AcademicQuestionsEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.logic.services.examsImplementations.AcademicQuestionsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExamAcademicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AcademicQuestionsService academicQuestionsService;

    @Test
    public void testObtenerPreguntas() throws Exception {
        // Datos de ejemplo
        AcademicQuestionsEntity question = new AcademicQuestionsEntity();
        question.setId(1);
        question.setQuestionText("¿Cuál es la capital de Francia?");
        question.setQuestionGrade(Grades.FIFTH);

        List<AcademicQuestionsEntity> questionsList = Collections.singletonList(question);

        // Simular el comportamiento del servicio
        when(academicQuestionsService.obtenerTodasLasPreguntas()).thenReturn(questionsList);

        // Realizar la solicitud GET
        mockMvc.perform(get("/api/Academic"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].questionText").value("¿Cuál es la capital de Francia?"))
                .andExpect(jsonPath("$[0].questionGrade").value("FIFTH"));
    }

    @Test
    public void testModificarPregunta() throws Exception {
        // Datos de ejemplo
        AcademicQuestionsEntity question = new AcademicQuestionsEntity();
        question.setId(1);
        question.setQuestionText("¿Cuál es la capital de Alemania?");
        question.setQuestionGrade(Grades.SIXTH);

        // Simular el comportamiento del servicio
        when(academicQuestionsService.modificarPregunta(eq(1), anyString(), any(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn(question);

        // JSON de ejemplo para modificar la pregunta
        String requestBody = "{ \"questionText\": \"¿Cuál es la capital de Alemania?\", \"questionGrade\": \"SIXTH\", \"imageUrl\": \"http://example.com/imagen.jpg\", \"optionA\": \"A\", \"optionB\": \"B\", \"optionC\": \"C\", \"optionD\": \"D\", \"correctOption\": \"A\" }";

        // Realizar la solicitud PUT
        mockMvc.perform(put("/api/Academic/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questionText").value("¿Cuál es la capital de Alemania?"))
                .andExpect(jsonPath("$.questionGrade").value("SIXTH"));
    }

    @Test
    public void testEliminarPregunta() throws Exception {
        mockMvc.perform(delete("/api/Academic/1"))
                .andExpect(status().isNoContent());
    }

}
