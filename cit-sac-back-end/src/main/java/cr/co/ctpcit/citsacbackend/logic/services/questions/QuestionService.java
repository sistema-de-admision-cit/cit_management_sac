package cr.co.ctpcit.citsacbackend.logic.services.questions;

import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionDto;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

public interface QuestionService {
    void createQuestion(QuestionDto questionDto);
    List<QuestionDto> getQuestions();
}
