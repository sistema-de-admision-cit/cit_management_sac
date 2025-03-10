package cr.co.ctpcit.citsacbackend.logic.services.questions;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionLevel;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import cr.co.ctpcit.citsacbackend.data.enums.SelectionType;
import cr.co.ctpcit.citsacbackend.data.repositories.questions.QuestionRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionDto;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionFilterSpec;
import cr.co.ctpcit.citsacbackend.logic.mappers.questions.QuestionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuestionsServiceImplTest {

  @Mock
  private QuestionRepository questionRepository;

  @InjectMocks
  private QuestionsServiceImpl questionsService;

  @BeforeEach
  public void setup() {
    // The @InjectMocks annotation automatically injects the mock into the service.
  }

  @Test
  public void testCreateQuestion() {
    QuestionDto sampleDto = createSampleQuestionDto();
    QuestionEntity sampleEntity = QuestionMapper.dtoToEntity(sampleDto);
    when(questionRepository.save(any(QuestionEntity.class))).thenReturn(sampleEntity);

    questionsService.createQuestion(sampleDto, null);

    verify(questionRepository, times(1)).save(any(QuestionEntity.class));
  }

  @Test
  public void testGetQuestions() {
    Pageable pageable = PageRequest.of(0, 10);
    QuestionFilterSpec filter =
        new QuestionFilterSpec(false, QuestionType.ACA, Grades.FIRST, QuestionLevel.EASY);

    QuestionEntity sampleEntity = createSampleQuestionEntity();
    Page<QuestionEntity> entityPage = new PageImpl<>(Collections.singletonList(sampleEntity));
    when(questionRepository.findAll(filter, pageable)).thenReturn(entityPage);

    Page<QuestionDto> dtoPage = questionsService.getQuestions(filter, pageable);

    assertNotNull(dtoPage);
    assertEquals(1, dtoPage.getTotalElements());
    assertEquals(sampleEntity.getId(), dtoPage.getContent().getFirst().id());
  }

  @Test
  public void testGetQuestionById_Found() {
    Long id = 1L;
    QuestionEntity sampleEntity = createSampleQuestionEntity();
    when(questionRepository.findById(id)).thenReturn(Optional.of(sampleEntity));

    QuestionDto resultDto = questionsService.getQuestionById(id);

    assertNotNull(resultDto);
    assertEquals(sampleEntity.getId(), resultDto.id());
  }

  @Test
  public void testGetQuestionById_NotFound() {
    Long id = 1L;
    when(questionRepository.findById(id)).thenReturn(Optional.empty());

    QuestionDto resultDto = questionsService.getQuestionById(id);

    assertNull(resultDto);
  }

  @Test
  public void testUpdateQuestion() {
    QuestionDto sampleDto = createSampleQuestionDto();
    QuestionEntity sampleEntity = QuestionMapper.dtoToEntity(sampleDto);
    when(questionRepository.save(any(QuestionEntity.class))).thenReturn(sampleEntity);
    when(questionRepository.findById(sampleDto.id())).thenReturn(Optional.of(sampleEntity));

    questionsService.updateQuestion(sampleDto, null);

    verify(questionRepository, times(1)).save(any(QuestionEntity.class));
  }

  @Test
  public void testDeleteQuestion() {
    Long questionId = 1L;

    questionsService.deleteQuestion(questionId);

    verify(questionRepository, times(1)).softDeleteQuestion(questionId);
  }

  @Test
  public void searchQuestionByText() {
    Pageable pageable = PageRequest.of(0, 10);
    String questionText = "favorite color";

    QuestionEntity sampleEntity = createSampleQuestionEntity();
    Page<QuestionEntity> entityPage = new PageImpl<>(Collections.singletonList(sampleEntity));
    when(questionRepository.findAllByQuestionTextContaining(questionText, pageable)).thenReturn(
        entityPage);

    Page<QuestionDto> dtoPage = questionsService.searchQuestion(questionText, pageable);

    assertNotNull(dtoPage);
    assertEquals(1, dtoPage.getTotalElements());
    assertEquals(sampleEntity.getId(), dtoPage.getContent().getFirst().id());
  }

  // Helper method to create a sample QuestionDto.
  private QuestionDto createSampleQuestionDto() {
    QuestionDto dto =
        new QuestionDto(1L, QuestionType.ACA, "What is your favorite color?", null, Grades.FOURTH,
            QuestionLevel.EASY, SelectionType.SINGLE, false, Collections.emptyList());

    return dto;
  }

  // Helper method to create a sample QuestionEntity.
  private QuestionEntity createSampleQuestionEntity() {
    QuestionEntity entity = new QuestionEntity();
    entity.setId(1L);
    entity.setQuestionText("What is your favorite color?");
    entity.setQuestionType(QuestionType.ACA);

    entity.setQuestionOptions(Collections.emptyList());
    return entity;
  }
}
