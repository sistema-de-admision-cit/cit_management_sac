package cr.co.ctpcit.citsacbackend.rest.exams.dai;

import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiExamQuestionsDto;
import cr.co.ctpcit.citsacbackend.logic.services.exams.dai.DaiExamQuestionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/dai-exam-questions")
@RequiredArgsConstructor
public class DaiExamQuestionsController {

  private final DaiExamQuestionsService service;

  @GetMapping("/{examId}/questions")
  public ResponseEntity<List<DaiExamQuestionsDto>> getExamQuestions(@PathVariable Integer examId) {
    List<DaiExamQuestionsDto> examAnswers = service.getExamAnswers(examId);
    return ResponseEntity.ok(examAnswers);
  }
}
