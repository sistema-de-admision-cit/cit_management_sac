package cr.co.ctpcit.citsacbackend.rest.exams;

import cr.co.ctpcit.citsacbackend.data.enums.ExamType;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.AcademicExamDetailsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiExamDetailsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.english.EnglishExamDetailsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.english.EnglishScoreEntryDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.StudentExamsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.logs.EnglishExamLogDto;
import cr.co.ctpcit.citsacbackend.logic.services.exams.ExamsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

  /**
   * Get the DAI exams list that a determined student has taken
   *
   * @param idNumber The student idNumber (Cédula)
   * @return The list of DAI exams
   */
  @GetMapping("/dai-exams/{idNumber}")
  public ResponseEntity<Iterable<DaiExamDetailsDto>> getDaiExams(@PathVariable String idNumber) {
    Iterable<DaiExamDetailsDto> academicExam = examsService.getExistingDaiExams(idNumber);

    return ResponseEntity.ok(academicExam);
  }

  /**
   * Get the English exams list that a determined student has taken
   *
   * @param idNumber The student idNumber (Cédula)
   * @return The list of English exams
   */
  @GetMapping("/english-exams/{idNumber}")
  public ResponseEntity<Iterable<EnglishExamDetailsDto>> getEnglishExams(
      @PathVariable String idNumber) {
    List<EnglishExamDetailsDto> englishExams = examsService.getExistingEnglishExams(idNumber);

    return ResponseEntity.ok(englishExams);
  }

  /**
   * Get students that have taken academic or DAI exams
   */
  @GetMapping("/students/{examType}")
  public ResponseEntity<Iterable<StudentExamsDto>> getStudentsByExamType(
      @PathVariable ExamType examType, @PageableDefault(page = 0, size = 25) Pageable pageable) {
    Iterable<StudentExamsDto> students = examsService.getStudentsByExamType(examType, pageable);

    return ResponseEntity.ok(students);
  }

  /**
   * Retrieves the number of students who have taken academic (ACA) exams.
   *
   * @return {@code 200 OK} with the count if available, or {@code 404 Not Found} if no data is returned
   */
  @GetMapping("/students-count-Academic")
  public ResponseEntity<Long> getEnrollmentsCountAcademicExams() {
    Long count = examsService.getStudentsCountWithAcademicExams();
    return count == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(count);
  }

  /**
   * Retrieves the number of students who have taken DAI exams.
   *
   * @return {@code 200 OK} with the count if available, or {@code 404 Not Found} if no data is returned
   */
  @GetMapping("/students-count-DAI")
  public ResponseEntity<Long> getEnrollmentsCountDAIExams() {
    Long count = examsService.getStudentsCountWithDAIExams();
    return count == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(count);
  }

  /**
   * Update comment and recommendation on DAI exam.
   */
  @PutMapping("/dai-exam")
  public ResponseEntity<Void> updateDaiExam(
      @Valid @RequestBody DaiExamDetailsDto daiExamDetailsDto) {
    examsService.updateDaiExam(daiExamDetailsDto);

    return ResponseEntity.noContent().build();
  }

  /**
   * Post English Exams Scores based on a CSV file provided by TrackTest.
   *
   * @return The list of English exam logs with the accepted or rejected scores.
   */
  @PostMapping("/update-scores")
  public ResponseEntity<List<EnglishExamLogDto>> uploadEnglishScores(
      @RequestBody List<EnglishScoreEntryDTO> englishScores) {
    List<EnglishExamLogDto> logs = examsService.processEnglishScores(englishScores);

    if (logs.isEmpty()) {
      return ResponseEntity.badRequest().body(logs);
    }

    return ResponseEntity.ok(logs);
  }

  /**
   * Search for academic or DAI exam based on IdNumber or any other value.
   */
  @GetMapping("/search/{value}/{examType}")
  public ResponseEntity<Iterable<StudentExamsDto>> searchStudentExams(@PathVariable String value,
      @PathVariable ExamType examType, @PageableDefault(page = 0, size = 25) Pageable pageable) {
    if(examType.equals(ExamType.ENG)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    if (value.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    Iterable<StudentExamsDto> students = examsService.searchStudentExams(value, examType, pageable);

    return ResponseEntity.ok(students);
  }

  /**
   * Searches and counts the number of students who have taken academic (ACA) exams
   * matching the given search value. The search is performed against student ID,
   * first name, and surnames.
   *
   * @param value the search term to filter students
   * @return {@code 200 OK} with the count if found, or {@code 404 Not Found} if no matches
   */
  @GetMapping("/search-count-academic")
  public ResponseEntity<Long> searchStudentExamsAcademicByValue(
          @NotNull @RequestParam String value) {
    Long count = examsService.getSearchCountByAcademicExam(value);

    return count == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(count);
  }

  /**
   * Searches and counts the number of students who have taken DAI exams
   * matching the given search value. The search is performed against student ID,
   * first name, and surnames.
   *
   * @param value the search term to filter students
   * @return {@code 200 OK} with the count if found, or {@code 404 Not Found} if no matches
   */

  @GetMapping("/search-count-DAI")
  public ResponseEntity<Long> searchStudentExamsDAIByValue(
          @NotNull @RequestParam String value) {
    Long count = examsService.getSearchCountByDAIExam(value);

    return count == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(count);
  }

}
