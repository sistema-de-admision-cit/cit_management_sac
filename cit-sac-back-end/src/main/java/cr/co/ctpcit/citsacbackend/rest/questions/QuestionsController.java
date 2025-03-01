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



@RestController
@RequestMapping("/api/questions")
public class QuestionsController {
  private final QuestionsServiceImpl questionService;

  public QuestionsController(QuestionsServiceImpl questionService) {
    this.questionService = questionService;
  }

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

  

  @GetMapping("/get-all")
  public ResponseEntity<Page<QuestionDto>> getAllQuestions(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
      @RequestParam(required = false) Boolean deleted,
      @RequestParam(required = false) QuestionType questionType,
      @RequestParam(required = false) Grades grade,
      @RequestParam(required = false) QuestionLevel questionLevel) {

    Pageable pageable = PageRequest.of(page, size);

    QuestionFilterSpec filter = new QuestionFilterSpec(deleted, questionType, grade, questionLevel);

    Page<QuestionDto> questionsPage = questionService.getQuestions(filter, pageable);

    return ResponseEntity.ok(questionsPage);
  }

  @GetMapping("/get-by-id/{id}")
  public ResponseEntity<QuestionDto> getQuestionById(@PathVariable Long id) {
    return ResponseEntity.ok(questionService.getQuestionById(id));
  }

  @PutMapping("/update")
  public ResponseEntity<QuestionDto> updateQuestion(@RequestBody QuestionDto questionDto) {
    try {
      return ResponseEntity.ok(questionService.updateQuestion(questionDto));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }
  }

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

  @GetMapping("/search")
  public ResponseEntity<Page<QuestionDto>> searchQuestion(@RequestParam String questionText,
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return ResponseEntity.ok(questionService.searchQuestion(questionText, pageable));
  }

  @GetMapping("/health")
  public ResponseEntity<String> healthCheck() {
    return ResponseEntity.ok("Questions service is up and running.");
  }
}
