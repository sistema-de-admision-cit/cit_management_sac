package cr.co.ctpcit.citsacbackend.logic.services.questions;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.questions.QuestionRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionDto;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionFilterSpec;
import cr.co.ctpcit.citsacbackend.logic.mappers.questions.QuestionMapper;
import cr.co.ctpcit.citsacbackend.logic.services.files.FileStorageServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service implementation for managing questions.
 */
@Service
public class QuestionsServiceImpl implements QuestionService {
  private final QuestionRepository questionRepository;
  private final FileStorageServiceImpl fileStorageService;

  /**
   * Constructor for QuestionsServiceImpl.
   *
   * @param questionRepository The repository for managing question entities.
   * @param fileStorageService The service for handling file storage.
   */
  public QuestionsServiceImpl(QuestionRepository questionRepository,
      FileStorageServiceImpl fileStorageService) {
    this.questionRepository = questionRepository;
    this.fileStorageService = fileStorageService;
  }

  /**
   * Creates a new question and uploads an optional file.
   *
   * @param questionDto The question data transfer object.
   * @param file        The optional file (image, PDF, etc.).
   * @return The created QuestionDto.
   */
  @Override
  public QuestionDto createQuestion(QuestionDto questionDto, MultipartFile file) {
    try {
      String fileUrl = null;

      if (file != null && !file.isEmpty()) {
        fileUrl = fileStorageService.storeFile(file, questionDto.questionText(), "questions");
      }

      QuestionEntity questionEntity = QuestionMapper.dtoToEntity(questionDto);
      questionEntity.setImageUrl(fileUrl); // Save the file URL in the entity

      QuestionEntity questionSaved = questionRepository.save(questionEntity);

      return QuestionMapper.entityToDto(questionSaved);
    } catch (Exception e) {
      throw new RuntimeException("Error creating question", e);
    }
  }

  /**
   * Retrieves a paginated list of questions based on filter criteria.
   *
   * @param filterSpec The filter specification for querying questions.
   * @param pageable   The pagination details.
   * @return A page of QuestionDto objects.
   */
  @Override
  public Page<QuestionDto> getQuestions(QuestionFilterSpec filterSpec, Pageable pageable) {
    return questionRepository.findAll(filterSpec, pageable).map(QuestionMapper::entityToDto);
  }

  /**
   * Retrieves a question by its ID.
   *
   * @param id The ID of the question.
   * @return The corresponding QuestionDto, or null if not found.
   */
  @Override
  public QuestionDto getQuestionById(Long id) {
    return questionRepository.findById(id).map(QuestionMapper::entityToDto).orElse(null);
  }

  /**
   * Updates an existing question.
   *
   * @param questionDto The updated question data.
   * @return The updated QuestionDto.
   */
  @Override
  public QuestionDto updateQuestion(QuestionDto questionDto) {
    QuestionEntity questionEntity = QuestionMapper.dtoToEntity(questionDto);

    QuestionEntity questionUpdated = questionRepository.save(questionEntity);

    return QuestionMapper.entityToDto(questionUpdated);
  }

  /**
   * Soft deletes a question by its ID.
   *
   * @param id The ID of the question to delete.
   */
  @Override
  public void deleteQuestion(Long id) {
    questionRepository.softDeleteQuestion(id);
  }

  /**
   * Searches for questions containing the specified text.
   *
   * @param questionText The text to search for in questions.
   * @param pageable     The pagination details.
   * @return A page of QuestionDto objects matching the search criteria.
   */
  @Override
  public Page<QuestionDto> searchQuestion(String questionText, Pageable pageable) {
    return questionRepository.findAllByQuestionTextContaining(questionText, pageable)
        .map(QuestionMapper::entityToDto);
  }
}
