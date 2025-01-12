package cr.co.ctpcit.citsacbackend.rest.exams.academic;

import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.AcademicExamQuestionsDto;
import cr.co.ctpcit.citsacbackend.logic.services.exams.academic.AcademicExamQuestionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/academic-exam-questions")
@RequiredArgsConstructor
public class AcademicExamQuestionsController {

  private final AcademicExamQuestionsService service;

  /**
   * Obtiene las preguntas y respuestas de un examen académico por ID
   *
   * @param examId ID del examen académico
   * @return Lista de preguntas del examen académico
   */
  @PreAuthorize("hasAuthority('SCOPE_S') or hasAuthority('SCOPE_A')")
  @GetMapping("/{examId}/questions")
  public ResponseEntity<List<AcademicExamQuestionsDto>> getAcademicExamQuestions(
      @PathVariable Integer examId) {
    List<AcademicExamQuestionsDto> examAnswers = service.getAcademicExamAnswers(examId);
    return ResponseEntity.ok(examAnswers);
  }
}
