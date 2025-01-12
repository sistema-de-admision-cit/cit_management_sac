package cr.co.ctpcit.citsacbackend.logic.services.exams.dai;


import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiQuestionsDto;

import java.util.List;

public interface DaiQuestionsService {
  List<DaiQuestionsDto> getAllDaiQuestions();

  DaiQuestionsDto getDaiQuestionById(Integer id);

  List<DaiQuestionsDto> getDaiQuestionByQuestionText(String questionText);

  void deleteDaiQuestion(Integer id);

  DaiQuestionsDto modifyDaiQuestion(Integer id, DaiQuestionsDto preguntaDto);
}
