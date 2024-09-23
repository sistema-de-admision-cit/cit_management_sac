package cr.co.ctpcit.citsacbackend.rest.exams.english;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/exams/english")
public class EnglishExamController {
  @GetMapping("/health")
  public String getEnglishExams() {
    return "English exams are healthy!";
  }

}
