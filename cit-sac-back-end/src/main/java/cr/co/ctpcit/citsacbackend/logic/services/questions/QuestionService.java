package cr.co.ctpcit.citsacbackend.logic.services.questions;

import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionDto;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionFilterSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface QuestionService {
  QuestionDto createQuestion(QuestionDto questionDto, MultipartFile file);

  Page<QuestionDto> getQuestions(QuestionFilterSpec filterSpec, Pageable pageable);

  QuestionDto getQuestionById(Long id);

  QuestionDto updateQuestion(QuestionDto questionDto, MultipartFile file);

  void deleteQuestion(Long id);

  Page<QuestionDto> searchQuestion(String questionText, Pageable pageable);
}
