package cr.co.ctpcit.citsacbackend.rest.questions;

import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/questions")
public class QuestionsController {

  @PostMapping("/add")
  public ResponseEntity<Void> createQuestion(QuestionDto questionDto) {

    return ResponseEntity.ok().build();
  }
}
