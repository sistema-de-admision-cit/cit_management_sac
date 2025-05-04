package cr.co.ctpcit.citsacbackend.rest.questions;

import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionLevel;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionDto;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionFilterSpec;
import cr.co.ctpcit.citsacbackend.logic.services.questions.QuestionsServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
/**
 * REST controller for managing question-related operations.
 * Provides endpoints for creating, retrieving, updating, and deleting questions,
 * as well as searching and filtering questions.
 */
@RestController
@RequestMapping("/api/questions")
public class QuestionsController {
  private final QuestionsServiceImpl questionService;
  /**
   * Constructs a new QuestionsController with the specified QuestionsServiceImpl.
   *
   * @param questionService the service to handle question operations
   */
  public QuestionsController(QuestionsServiceImpl questionService) {
    this.questionService = questionService;
  }
  /**
   * Creates a new question with optional file attachment.
   *
   * @param questionDto the question data to create
   * @param file the optional file to attach to the question
   * @return ResponseEntity containing the created QuestionDto
   */
  @PostMapping(value = "/create", consumes = {"multipart/form-data"})
  public ResponseEntity<QuestionDto> createQuestion(
      @RequestPart("question") QuestionDto questionDto,
      @RequestPart(value = "file", required = false) MultipartFile file) {
    try {
      QuestionDto savedQuestion = questionService.createQuestion(questionDto, file);
      return ResponseEntity.ok(savedQuestion);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }
  }
  /**
   * Retrieves all questions with optional filtering and pagination.
   *
   * @param page the page number (default: 0)
   * @param size the page size (default: 10)
   * @param questionText filter by question text (optional)
   * @param deleted filter by deleted status (default: false)
   * @param questionType filter by question type (optional)
   * @param grade filter by grade level (optional)
   * @param questionLevel filter by question difficulty level (optional)
   * @return ResponseEntity containing a Page of QuestionDto
   */
  @GetMapping("/get-all")
  public ResponseEntity<Page<QuestionDto>> getAllQuestions(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
      @RequestParam(required = false, defaultValue = "") String questionText,
      @RequestParam(required = false, defaultValue = "false") Boolean deleted,
      @RequestParam(required = false) QuestionType questionType,
      @RequestParam(required = false) Grades grade,
      @RequestParam(required = false) QuestionLevel questionLevel) {

    Pageable pageable = PageRequest.of(page, size);

    QuestionFilterSpec filter =
        new QuestionFilterSpec(questionText, deleted, questionType, grade, questionLevel);

    Page<QuestionDto> questionsPage = questionService.getQuestions(filter, pageable);

    return ResponseEntity.ok(questionsPage);
  }
  /**
   * Retrieves a question by its ID.
   *
   * @param id the ID of the question to retrieve
   * @return ResponseEntity containing the QuestionDto
   */
  @GetMapping("/get-by-id/{id}")
  public ResponseEntity<QuestionDto> getQuestionById(@PathVariable Long id) {
    return ResponseEntity.ok(questionService.getQuestionById(id));
  }
  /**
   * Updates an existing question with optional file attachment.
   *
   * @param questionDto the updated question data
   * @param file the optional file to attach to the question
   * @return ResponseEntity containing the updated QuestionDto
   */
  @PostMapping(value = "/update", consumes = {"multipart/form-data"})
  public ResponseEntity<QuestionDto> updateQuestion(
      @RequestPart("question") QuestionDto questionDto,
      @RequestPart(value = "file", required = false) MultipartFile file) {
    try {
      return ResponseEntity.ok(questionService.updateQuestion(questionDto, file));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }
  }
  /**
   * Deletes a question by its ID.
   *
   * @param id the ID of the question to delete
   * @return ResponseEntity with success message or error status
   */
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteQuestion(@PathVariable Long id) {
    try {
      questionService.deleteQuestion(id);
      return ResponseEntity.ok("Question deleted successfully.");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }
  }
  /**
   * Searches questions by text with pagination.
   *
   * @param questionText the text to search for in questions
   * @param page the page number (default: 0)
   * @param size the page size (default: 10)
   * @return ResponseEntity containing a Page of matching QuestionDto
   */
  @GetMapping("/search")
  public ResponseEntity<Page<QuestionDto>> searchQuestion(@RequestParam String questionText,
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return ResponseEntity.ok(questionService.searchQuestion(questionText, pageable));
  }
  /**
   * Health check endpoint for the questions service.
   *
   * @return ResponseEntity with service status message
   */
  @GetMapping("/health")
  public ResponseEntity<String> healthCheck() {
    return ResponseEntity.ok("Questions service is up and running.");
  }
}
