package cr.co.ctpcit.citsacbackend.logic.services;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiExamQuestionsDto;

import java.util.List;

public interface DaiExamQuestionsService {
    List<DaiExamQuestionsDto> getExamAnswers(Integer examId);
}
