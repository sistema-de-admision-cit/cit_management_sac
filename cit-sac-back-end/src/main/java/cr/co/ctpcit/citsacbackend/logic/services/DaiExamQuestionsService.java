package cr.co.ctpcit.citsacbackend.logic.services;

import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiExamQuestionsDto;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public interface DaiExamQuestionsService {
    List<DaiExamQuestionsDto> getAllExamQuestions();
    DaiExamQuestionsDto addExamQuestion(@NotNull DaiExamQuestionsDto dto);
}
