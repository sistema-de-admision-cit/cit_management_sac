package cr.co.ctpcit.citsacbackend.logic.services.questions;

import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionDto;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionFilterSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {
  QuestionDto createQuestion(QuestionDto questionDto);

  Page<QuestionDto> getQuestions(QuestionFilterSpec filterSpec, Pageable pageable);

  QuestionDto getQuestionById(Long id);

  QuestionDto updateQuestion(QuestionDto questionDto);

  void deleteQuestion(Long id);

  Page<QuestionDto> searchQuestion(String questionText, Pageable pageable);
}
