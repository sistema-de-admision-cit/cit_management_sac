package cr.co.ctpcit.citsacbackend.logic.services.exams.academic;

import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.AcademicQuestionsDto;

import java.util.List;

public interface AcademicQuestionsService {
  List<AcademicQuestionsDto> getAllAcademicQuestions();

  AcademicQuestionsDto getAcademicQuestionById(Integer id);

  List<AcademicQuestionsDto> getAcademicQuestionByQuestionText(String questionText);

  void deleteAcademicQuestion(Integer id);

  AcademicQuestionsDto modifyAcademicQuestion(Integer id, AcademicQuestionsDto preguntaDto);
}
