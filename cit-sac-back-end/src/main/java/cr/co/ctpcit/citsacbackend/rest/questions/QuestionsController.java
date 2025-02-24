package cr.co.ctpcit.citsacbackend.rest.questions;

import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionDto;
import cr.co.ctpcit.citsacbackend.logic.services.questions.QuestionsServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/questions")
public class QuestionsController {
  private final QuestionsServiceImpl questionService;

  public QuestionsController(QuestionsServiceImpl questionService) {
    this.questionService = questionService;
  }

  @PostMapping("/create")
  public ResponseEntity<Void> createQuestion(@RequestBody QuestionDto questionDto) {
    System.out.println("Creating question...");
    System.out.println(questionDto);
    try {
      questionService.createQuestion(questionDto);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok().build();
  }

  @GetMapping("/get-all")
  public ResponseEntity<Page<QuestionDto>> getAllQuestions(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

    Pageable pageable = PageRequest.of(page, size);

    Page<QuestionDto> questionsPage = questionService.getQuestions(pageable);

    return ResponseEntity.ok(questionsPage);
  }

  @GetMapping("/get-by-type/{type}")
  public ResponseEntity<Page<QuestionDto>> getQuestionsByType(@PathVariable QuestionType type,
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);

    Page<QuestionDto> questionsPage = questionService.getQuestionsByType(type, pageable);

    return ResponseEntity.ok(questionsPage);
  }

  @GetMapping("/get-by-id/{id}")
  public ResponseEntity<QuestionDto> getQuestionById(@PathVariable Long id) {
    return ResponseEntity.ok(questionService.getQuestionById(id));
  }

  @GetMapping("/health")
  public ResponseEntity<String> healthCheck() {
    return ResponseEntity.ok("Questions service is up and running.");
  }
}
