package cr.co.ctpcit.citsacbackend.rest.exams.english;

import cr.co.ctpcit.citsacbackend.logic.services.exams.english.EnglishExamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/exams/english")
public class EnglishExamController {
  private final EnglishExamService englishExamService;

  public EnglishExamController(EnglishExamService englishExamService) {
    this.englishExamService = englishExamService;
  }

  @GetMapping("/health")
  public String getEnglishExams() {
    return "English exams are healthy!";
  }

}
