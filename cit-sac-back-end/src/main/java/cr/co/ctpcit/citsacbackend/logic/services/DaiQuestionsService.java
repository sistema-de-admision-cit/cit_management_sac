package cr.co.ctpcit.citsacbackend.logic.services;


import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiQuestionsDto;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public interface DaiQuestionsService {
    List<DaiQuestionsDto> obtenerTodasLasPreguntas();
    DaiQuestionsDto obtenerPreguntaPorId(Integer id);
    List<DaiQuestionsDto> obtenerPreguntasPorQuestionText(String questionText);
    void eliminarPregunta(Integer id);
    DaiQuestionsDto modificarPregunta(Integer id, DaiQuestionsDto preguntaDto);
}
