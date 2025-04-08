package cr.co.ctpcit.citsacbackend.logic.services.questions;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionOptionEntity;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import cr.co.ctpcit.citsacbackend.data.repositories.questions.QuestionOptionRepository;
import cr.co.ctpcit.citsacbackend.data.repositories.questions.QuestionRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionDto;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionFilterSpec;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionOptionDto;
import cr.co.ctpcit.citsacbackend.logic.mappers.questions.QuestionMapper;
import cr.co.ctpcit.citsacbackend.logic.services.files.FileStorageServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
      FileStorageServiceImpl fileStorageService,
      QuestionOptionRepository quesitonOptionRepository) {
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
   * @param file        The optional new file (image, PDF, etc.).
   * @return The updated QuestionDto.
   */
  @Override
  @Transactional
  public QuestionDto updateQuestion(QuestionDto questionDto, MultipartFile file) {
    QuestionEntity existingQuestion = questionRepository.findById(questionDto.id()).orElseThrow(
        () -> new EntityNotFoundException("Question not found with id: " + questionDto.id()));

    // Update the file if provided
    updateFileIfProvided(existingQuestion, file, questionDto.questionText());

    // Handle changes in question type and options
    boolean typeChanged = !existingQuestion.getQuestionType().equals(questionDto.questionType());
    if (typeChanged) {
      updateQuestionType(existingQuestion, questionDto);
    } else if (questionDto.questionType() == QuestionType.ACA && questionDto.questionOptions() != null) {
      syncAcaOptions(existingQuestion, questionDto);
    }

    updateBasicFields(existingQuestion, questionDto);

    QuestionEntity updatedQuestion = questionRepository.save(existingQuestion);
    return QuestionMapper.entityToDto(updatedQuestion);
  }

  /**
   * Updates the file associated with the question if a new file is provided.
   *
   * @implNote causes a side effect on the question entity.
   */
  private void updateFileIfProvided(QuestionEntity question, MultipartFile file,
      String questionText) {
    if (file != null && !file.isEmpty()) {
      try {
        String fileUrl = fileStorageService.storeFile(file, questionText, "questions");
        question.setImageUrl(fileUrl);
      } catch (Exception e) {
        throw new RuntimeException("Error uploading file", e);
      }
    }
  }

  /**
   * Updates the question type. Also handles specific actions when switching between ACA and DAI
   * types.
   *
   * @implNote causes a side effect on the question entity.
   */
  private void updateQuestionType(QuestionEntity question, QuestionDto dto) {
    QuestionType existingType = question.getQuestionType();
    QuestionType newType = dto.questionType();

    // Handle type change: ACA -> DAI or DAI -> ACA
    if (existingType == QuestionType.ACA && newType == QuestionType.DAI) {
      // From ACA to DAI: clear all options
      question.getQuestionOptions().clear();
    } else if (existingType == QuestionType.DAI && newType == QuestionType.ACA) {
      // From DAI to ACA: expect exactly 4 options in the DTO
      List<QuestionOptionDto> options = dto.questionOptions();
      if (options == null || options.size() != 4) {
        throw new IllegalArgumentException(
            "Para preguntas ACA se requieren exactamente 4 opciones.");
      }
      question.getQuestionOptions().clear();
      options.forEach(
          optionDto -> question.addQuestionOption(createQuestionOption(optionDto, question)));
    }
    question.setQuestionType(newType);
  }

  /**
   * Synchronizes ACA type question options when the question type remains ACA.
   *
   * @implNote causes a side effect on the question entity.
   */
  private void syncAcaOptions(QuestionEntity question, QuestionDto dto) {
    List<QuestionOptionDto> newOptions = dto.questionOptions();
    if (newOptions.size() != 4) {
      throw new IllegalArgumentException("Para preguntas ACA se requieren exactamente 4 opciones.");
    }

    List<QuestionOptionEntity> currentOptions = question.getQuestionOptions();
    if (currentOptions.size() == 4) {
      // Update the existing option entities
      for (int i = 0; i < 4; i++) {
        QuestionOptionDto optionDto = newOptions.get(i);
        QuestionOptionEntity optionEntity = currentOptions.get(i);
        optionEntity.setOption(optionDto.option());
        optionEntity.setIsCorrect(optionDto.isCorrect());
      }
    } else {
      // If current options are not exactly 4, clear and add the new ones
      currentOptions.clear();
      newOptions.forEach(
          optionDto -> question.addQuestionOption(createQuestionOption(optionDto, question)));
    }
    System.out.println("After processing options: " + question.getQuestionOptions().size());
  }

  /**
   * Updates the common fields of the question.
   *
   * @implNote causes a side effect on the question entity.
   */
  private void updateBasicFields(QuestionEntity question, QuestionDto dto) {
    question.setQuestionGrade(dto.questionGrade());
    question.setQuestionText(dto.questionText());
    question.setQuestionLevel(dto.questionLevel());
    // Note: selectionType is not handled in this implementation.
  }

  /**
   * Creates a new QuestionOptionEntity from a given DTO.
   */
  private QuestionOptionEntity createQuestionOption(QuestionOptionDto optionDto,
      QuestionEntity question) {
    return QuestionOptionEntity.builder().option(optionDto.option())
        .isCorrect(optionDto.isCorrect()).question(question).build();
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
