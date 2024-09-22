package cr.co.ctpcit.citsacbackend.logic.services;

import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.AcademicQuestionsDto;

import java.util.List;

public interface AcademicQuestionsService {
  List<AcademicQuestionsDto> obtenerTodasLasPreguntas();

  AcademicQuestionsDto obtenerPreguntaPorId(Integer id);

  List<AcademicQuestionsDto> obtenerPreguntasPorQuestionText(String questionText);

  void eliminarPregunta(Integer id);

  AcademicQuestionsDto modificarPregunta(Integer id, AcademicQuestionsDto preguntaDto);
}
