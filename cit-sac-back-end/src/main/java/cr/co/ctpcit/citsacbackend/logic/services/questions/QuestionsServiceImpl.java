package cr.co.ctpcit.citsacbackend.logic.services.questions;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionOptionEntity;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import cr.co.ctpcit.citsacbackend.data.repositories.questions.QuestionRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.questions.QuestionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionsServiceImpl implements QuestionService {
  private final QuestionRepository questionRepository;

  public QuestionsServiceImpl(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  @Override
  public void createQuestion(QuestionDto questionDto) {
    QuestionEntity questionEntity = QuestionMapper.dtoToEntity(questionDto);
    List<QuestionOptionEntity> options = questionEntity.getQuestionOptions();

    QuestionEntity saved = questionRepository.save(questionEntity);
  }

  @Override
  public Page<QuestionDto> getQuestions(Pageable pageable) {
    return questionRepository.findAll(pageable).map(QuestionMapper::entityToDto);
  }

  @Override
  public QuestionDto getQuestionById(Long id) {
    return questionRepository.findById(id).map(QuestionMapper::entityToDto).orElse(null);
  }

  @Override
  public Page<QuestionDto> getQuestionsByType(QuestionType questionType, Pageable pageable) {
    return questionRepository.findAllByQuestionType(questionType, pageable)
        .map(QuestionMapper::entityToDto);
  }
}


