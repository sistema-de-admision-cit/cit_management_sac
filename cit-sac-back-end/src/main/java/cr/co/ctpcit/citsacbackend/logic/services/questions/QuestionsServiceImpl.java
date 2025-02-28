package cr.co.ctpcit.citsacbackend.logic.services.questions;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.questions.QuestionRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionDto;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionFilterSpec;
import cr.co.ctpcit.citsacbackend.logic.mappers.questions.QuestionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionsServiceImpl implements QuestionService {
  private final QuestionRepository questionRepository;

  public QuestionsServiceImpl(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  @Override
  public QuestionDto createQuestion(QuestionDto questionDto) {
    QuestionEntity questionEntity = QuestionMapper.dtoToEntity(questionDto);

    questionEntity.setDeleted(null);

    QuestionEntity questionSaved = questionRepository.save(questionEntity);

    return QuestionMapper.entityToDto(questionSaved);
  }

  @Override
  public Page<QuestionDto> getQuestions(QuestionFilterSpec filterSpec, Pageable pageable) {
    return questionRepository.findAll(filterSpec, pageable).map(QuestionMapper::entityToDto);
  }

  @Override
  public QuestionDto getQuestionById(Long id) {
    return questionRepository.findById(id).map(QuestionMapper::entityToDto).orElse(null);
  }

  @Override
  public QuestionDto updateQuestion(QuestionDto questionDto) {
    QuestionEntity questionEntity = QuestionMapper.dtoToEntity(questionDto);

    QuestionEntity questionUpdated = questionRepository.save(questionEntity);

    return QuestionMapper.entityToDto(questionUpdated);
  }

  @Override
  public void deleteQuestion(Long id) {
    questionRepository.softDeleteQuestion(id);
  }
}


