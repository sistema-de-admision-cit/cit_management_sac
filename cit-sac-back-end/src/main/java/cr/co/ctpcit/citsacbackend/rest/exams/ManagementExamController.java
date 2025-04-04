package cr.co.ctpcit.citsacbackend.rest.exams;

import cr.co.ctpcit.citsacbackend.data.enums.ExamType;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.AcademicExamDetailsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.DaiExamDetailsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.EnglishScoreEntryDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.StudentExamsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.logs.EnglishExamLogDto;
import cr.co.ctpcit.citsacbackend.logic.services.exams.ExamsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/management-exams")
@RequiredArgsConstructor
@Validated
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
  public ResponseEntity<Iterable<StudentExamsDto>> getStudentsByExamType(
      @PathVariable ExamType examType, @PageableDefault(page = 0, size = 25) Pageable pageable) {
    Iterable<StudentExamsDto> students = examsService.getStudentsByExamType(examType, pageable);

    return ResponseEntity.ok(students);
  }

  //TODO: Search by idNumber, name, lastName, and ExamType.

  @PutMapping("/dai-exam")
  public ResponseEntity<Void> updateDaiExam(@Valid @RequestBody DaiExamDetailsDto daiExamDetailsDto) {
    examsService.updateDaiExam(daiExamDetailsDto);

    return ResponseEntity.noContent().build();
  }

  @PostMapping("/update-scores")
  public ResponseEntity<List<EnglishExamLogDto>> uploadEnglishScores(
      @RequestBody List<EnglishScoreEntryDTO> englishScores) {
    List<EnglishExamLogDto> logs = examsService.processEnglishScores(englishScores);

    if (logs.isEmpty()) {
      return ResponseEntity.badRequest().body(logs);
    }

    return ResponseEntity.ok(logs);
  }
}
