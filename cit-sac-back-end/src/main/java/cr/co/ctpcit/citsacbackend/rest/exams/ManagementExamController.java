package cr.co.ctpcit.citsacbackend.rest.exams;

import cr.co.ctpcit.citsacbackend.data.enums.ExamType;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.AcademicExamDetailsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.DaiExamDetailsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.StudentExamsDto;
import cr.co.ctpcit.citsacbackend.logic.services.exams.ExamsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<Iterable<AcademicExamDetailsDto>> getAcademicExams(
      @PathVariable String idNumber) {
    Iterable<AcademicExamDetailsDto> academicExam = examsService.getExistingAcademicExams(idNumber);

    return ResponseEntity.ok(academicExam);
  }

  @GetMapping("/dai-exams/{idNumber}")
  public ResponseEntity<Iterable<DaiExamDetailsDto>> getDaiExams(@PathVariable String idNumber) {
    Iterable<DaiExamDetailsDto> academicExam = examsService.getExistingDaiExams(idNumber);

    return ResponseEntity.ok(academicExam);
  }

  /**
   * Get students that have taken an academic exams
   */
  @GetMapping("/students/{examType}")
  public ResponseEntity<Iterable<StudentExamsDto>> getStudentsByExamType(@PathVariable ExamType examType) {
    Iterable<StudentExamsDto> students = examsService.getStudentsByExamType(examType);

    return ResponseEntity.ok(students);
  }
}
