package cr.co.ctpcit.citsacbackend.logic.services.questions;

import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionDto;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionFilterSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
/**
 * Service interface for managing questions and their associated operations.
 * Provides CRUD functionality for questions including creation, retrieval,
 * updating, and deletion, with support for file attachments and filtering.
 */
@Service
public interface QuestionService {
  /**
   * Creates a new question with an optional attached file.
   *
   * @param questionDto the question data transfer object containing question details
   * @param file the optional file to be attached to the question (can be null)
   * @return the created question with generated identifiers
   */
  QuestionDto createQuestion(QuestionDto questionDto, MultipartFile file);
  /**
   * Retrieves a paginated list of questions filtered by specification.
   *
   * @param filterSpec the filtering criteria for questions
   * @param pageable pagination information (page number, size, sorting)
   * @return a page of question DTOs matching the criteria
   */
  Page<QuestionDto> getQuestions(QuestionFilterSpec filterSpec, Pageable pageable);
  /**
   * Retrieves a single question by its unique identifier.
   *
   * @param id the unique identifier of the question
   * @return the question DTO
   */
  QuestionDto getQuestionById(Long id);
  /**
   * Updates an existing question and its optional attached file.
   *
   * @param questionDto the question data transfer object with updated information
   * @param file the optional new file to replace the existing attachment (can be null)
   * @return the updated question DTO
   */
  QuestionDto updateQuestion(QuestionDto questionDto, MultipartFile file);
  /**
   * Deletes a question by its unique identifier.
   *
   * @param id the unique identifier of the question to delete
   */
  void deleteQuestion(Long id);
  /**
   * Searches questions containing the specified text in their content.
   *
   * @param questionText the text to search for in question content
   * @param pageable pagination information (page number, size, sorting)
   * @return a page of matching question DTOs
   */
  Page<QuestionDto> searchQuestion(String questionText, Pageable pageable);
}
