package cr.co.ctpcit.citsacbackend.rest.exams;

import cr.co.ctpcit.citsacbackend.logic.dto.exams.ExamDto;
import cr.co.ctpcit.citsacbackend.logic.services.exams.ExamsService;
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
  public ResponseEntity<ExamDto> getAcademicExam(@PathVariable String id) {
    ExamDto academicExam = examsService.getAcademicExam(id);

    return ResponseEntity.ok(academicExam);
  }

  @PutMapping("/save-academic-exam")
  public ResponseEntity<Void> saveAcademicExam(@RequestBody ExamDto examDto) {
    examsService.saveAcademicExam(examDto);

    return ResponseEntity.noContent().build();
  }

}
