package cr.co.ctpcit.citsacbackend.logic.services.questions;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {
  QuestionDto createQuestion(QuestionDto questionDto);

  Page<QuestionDto> getQuestions(Pageable pageable);

  QuestionDto getQuestionById(Long id);

  Page<QuestionDto> getQuestionsByType(QuestionType questionType, Pageable pageable);

  QuestionDto updateQuestion(QuestionDto questionDto);
}
