package cr.co.ctpcit.citsacbackend.rest.exams;

import com.fasterxml.jackson.core.JsonProcessingException;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.ExamAcaDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.ExamDaiDto;
import cr.co.ctpcit.citsacbackend.logic.services.exams.ExamsService;
import cr.co.ctpcit.citsacbackend.logic.services.exams.english.EnglishExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
@Validated
public class ExamsController {
  private final ExamsService examsService;
  private final EnglishExamService englishExamService;

  @GetMapping("/academic-exam/{id}")
  public ResponseEntity<ExamAcaDto> getAcademicExam(@PathVariable String id) {
    ExamAcaDto academicExam = examsService.getAcademicExam(id);

    return ResponseEntity.ok(academicExam);
  }

  @PutMapping("/save-academic-exam")
  public ResponseEntity<Void> saveAcademicExam(@RequestBody @Valid ExamAcaDto examAcaDto)
      throws JsonProcessingException {
    examsService.saveAcademicExam(examAcaDto);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("/dai-exam/{id}")
    public ResponseEntity<ExamDaiDto> getDaiExam(@PathVariable String id) {
        ExamDaiDto daiExam = examsService.getDaiExam(id);

        return ResponseEntity.ok(daiExam);
    }

  @PutMapping("/save-dai-exam")
  public ResponseEntity<Void> saveDaiExam(@RequestBody @Valid ExamDaiDto examDaiDto)
      throws JsonProcessingException {
    examsService.saveDaiExam(examDaiDto);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("english-exam/get-exams")
  public ResponseEntity<List<EnglishExamEntity>> getEnglishExamScores() {
    return ResponseEntity.ok(englishExamService.getAll());
  }

  @PostMapping("english-exam/update-scores")
  public ResponseEntity<List<EnglishExamLogDto>> uploadEnglishScores(
      @RequestBody List<EnglishScoreEntryDTO> englishScores) {
    List<EnglishExamLogDto> logs = englishExamService.processEnglishScores(englishScores);

    if (logs.isEmpty()) {
      return ResponseEntity.badRequest().body(logs);
    }

    return ResponseEntity.ok(logs);
  }
}
