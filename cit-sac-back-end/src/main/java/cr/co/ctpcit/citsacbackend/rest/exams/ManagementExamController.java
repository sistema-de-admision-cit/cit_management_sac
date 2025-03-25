package cr.co.ctpcit.citsacbackend.rest.exams;

import cr.co.ctpcit.citsacbackend.logic.dto.exams.AcademicExamDto;
import cr.co.ctpcit.citsacbackend.logic.services.exams.ExamsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/management-exams")
@RequiredArgsConstructor
public class ManagementExamController {
  private final ExamsService examsService;

  /**
   * Get the academic exams list that a determined student has taken
   *
   * @param idNumber The student idNumber (Cédula)
   * @return The list of academic exams
   */
  @GetMapping("/academic-exams/{idNumber}")
  public ResponseEntity<Iterable<AcademicExamDto>> getAcademicExams(@PathVariable String idNumber) {
    Iterable<AcademicExamDto> academicExam = examsService.getExistingAcademicExams(idNumber);

    return ResponseEntity.ok(academicExam);
  }
}
