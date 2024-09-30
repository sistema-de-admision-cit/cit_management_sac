package cr.co.ctpcit.citsacbackend.rest.exams.english;

import cr.co.ctpcit.citsacbackend.data.entities.exams.english.EnglishExamEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.english.EnglishScoreEntryDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.logs.englishExams.EnglishExamLogDto;
import cr.co.ctpcit.citsacbackend.logic.services.exams.english.EnglishExamServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/exams/english")
public class EnglishExamController {
  private final EnglishExamServiceImplementation englishExamService;

  @Autowired
  public EnglishExamController(EnglishExamServiceImplementation englishExamService) {
    this.englishExamService = englishExamService;
  }

  @GetMapping("/health")
  public ResponseEntity<String> getEnglishExams() {
    return ResponseEntity.ok("English exams are healthy!");
  }

  @GetMapping("/get-exams")
  public ResponseEntity<List<EnglishExamEntity>> getEnglishExamScores() {
    return ResponseEntity.ok(englishExamService.getAll());
  }

  @PostMapping("/update-scores")
  public ResponseEntity<List<EnglishExamLogDto>> uploadEnglishScores(
      @RequestBody List<EnglishScoreEntryDTO> englishScores) {
    List<EnglishExamLogDto> logs = englishExamService.processEnglishScores(englishScores);

    if (logs.isEmpty()) {
      return ResponseEntity.badRequest().body(logs);
    }

    return ResponseEntity.ok(logs);
  }
}
