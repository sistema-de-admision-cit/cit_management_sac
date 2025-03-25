package cr.co.ctpcit.citsacbackend.rest.exams;

import com.fasterxml.jackson.core.JsonProcessingException;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.ExamAcaDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.ExamDaiDto;
import cr.co.ctpcit.citsacbackend.logic.services.exams.ExamsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
@Validated
public class ExamsController {
  private final ExamsService examsService;

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

}
