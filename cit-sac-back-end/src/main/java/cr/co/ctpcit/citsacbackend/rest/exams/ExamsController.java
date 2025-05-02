package cr.co.ctpcit.citsacbackend.rest.exams;

import com.fasterxml.jackson.core.JsonProcessingException;
import cr.co.ctpcit.citsacbackend.data.entities.exams.EnglishExamEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.EnglishScoreEntryDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.ExamAcaDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.ExamDaiDto;
import cr.co.ctpcit.citsacbackend.logic.dto.logs.EnglishExamLogDto;
import cr.co.ctpcit.citsacbackend.logic.services.exams.ExamsService;
import cr.co.ctpcit.citsacbackend.logic.services.exams.english.EnglishExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * REST controller for managing exam-related operations.
 * Provides endpoints for retrieving, saving, and processing various types of exams.
 */
@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
@Validated
public class ExamsController {
  private final ExamsService examsService;
  private final EnglishExamService englishExamService;
  /**
   * Retrieves an academic exam by its ID.
   *
   * @param id the unique identifier of the academic exam
   * @return ResponseEntity containing the ExamAcaDto
   */
  @GetMapping("/academic-exam/{id}")
  public ResponseEntity<ExamAcaDto> getAcademicExam(@PathVariable String id) {
    ExamAcaDto academicExam = examsService.getAcademicExam(id);

    return ResponseEntity.ok(academicExam);
  }
  /**
   * Saves an academic exam.
   *
   * @param examAcaDto the academic exam data to be saved
   * @return ResponseEntity with no content status
   * @throws JsonProcessingException if there's an error processing the exam data
   */
  @PutMapping("/save-academic-exam")
  public ResponseEntity<Void> saveAcademicExam(@RequestBody @Valid ExamAcaDto examAcaDto)
      throws JsonProcessingException {
    examsService.saveAcademicExam(examAcaDto);

    return ResponseEntity.noContent().build();
  }
  /**
   * Retrieves a DAI exam by its ID.
   *
   * @param id the unique identifier of the DAI exam
   * @return ResponseEntity containing the ExamDaiDto
   */
  @GetMapping("/dai-exam/{id}")
    public ResponseEntity<ExamDaiDto> getDaiExam(@PathVariable String id) {
        ExamDaiDto daiExam = examsService.getDaiExam(id);

        return ResponseEntity.ok(daiExam);
    }
  /**
   * Saves a DAI exam.
   *
   * @param examDaiDto the DAI exam data to be saved
   * @return ResponseEntity with no content status
   * @throws JsonProcessingException if there's an error processing the exam data
   */
  @PutMapping("/save-dai-exam")
  public ResponseEntity<Void> saveDaiExam(@RequestBody @Valid ExamDaiDto examDaiDto)
      throws JsonProcessingException {
    examsService.saveDaiExam(examDaiDto);

    return ResponseEntity.noContent().build();
  }
  /**
   * Retrieves all English exam scores.
   *
   * @return ResponseEntity containing a list of EnglishExamEntity
   */
  @GetMapping("english-exam/get-exams")
  public ResponseEntity<List<EnglishExamEntity>> getEnglishExamScores() {
    return ResponseEntity.ok(englishExamService.getAll());
  }
  /**
   * Updates English exam scores in bulk.
   *
   * @param englishScores list of English score entries to be processed
   * @return ResponseEntity containing a list of EnglishExamLogDto with processing results
   */
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
