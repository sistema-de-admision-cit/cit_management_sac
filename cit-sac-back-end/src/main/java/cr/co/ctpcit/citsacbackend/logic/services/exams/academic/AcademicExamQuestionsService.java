package cr.co.ctpcit.citsacbackend.logic.services.exams.academic;

import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.AcademicExamQuestionsDto;

import java.util.List;

public interface AcademicExamQuestionsService {
  List<AcademicExamQuestionsDto> getExamAnswers(Integer examId);
}
