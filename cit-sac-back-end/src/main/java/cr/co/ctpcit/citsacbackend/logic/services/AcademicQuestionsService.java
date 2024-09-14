package cr.co.ctpcit.citsacbackend.logic.services;

import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.AcademicQuestionsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiQuestionsDto;

import java.util.List;

public interface AcademicQuestionsService {
    List<AcademicQuestionsDto> obtenerTodasLasPreguntas();
    AcademicQuestionsDto obtenerPreguntaPorId(Integer id);
    void eliminarPregunta(Integer id);
    AcademicQuestionsDto modificarPregunta(Integer id, AcademicQuestionsDto preguntaDto);
}
