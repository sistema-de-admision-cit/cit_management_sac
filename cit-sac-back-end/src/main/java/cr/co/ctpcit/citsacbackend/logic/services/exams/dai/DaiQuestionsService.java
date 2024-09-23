package cr.co.ctpcit.citsacbackend.logic.services.exams.dai;


import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiQuestionsDto;

import java.util.List;

public interface DaiQuestionsService {
  List<DaiQuestionsDto> obtenerTodasLasPreguntas();

  DaiQuestionsDto obtenerPreguntaPorId(Integer id);

  List<DaiQuestionsDto> obtenerPreguntasPorQuestionText(String questionText);

  void eliminarPregunta(Integer id);

  DaiQuestionsDto modificarPregunta(Integer id, DaiQuestionsDto preguntaDto);
}
