package cr.co.ctpcit.citsacbackend.rest.exams.academic;

import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.AcademicQuestionsDto;
import cr.co.ctpcit.citsacbackend.logic.services.examsImplementations.AcademicQuestionsServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/questions-academic")
public class AcademicQuestionsController {

  /**
   * Obtener todas las preguntas de examen
   *
   * @return Lista de preguntas de examen
   */
  @PreAuthorize("hasAuthority('SCOPE_S') or hasAuthority('SCOPE_A') or hasAuthority('SCOPE_T')")
  @GetMapping
  public ResponseEntity<List<AcademicQuestionsDto>> getAllExamQuestions() {
    List<AcademicQuestionsDto> examQuestions =
        academicQuestionsServiceimplementation.obtenerTodasLasPreguntas();
    return ResponseEntity.ok(examQuestions);
  }

  /**
   * Busca una pregunta por ID
   *
   * @param id ID de la pregunta
   * @return Pregunta encontrada
   */
  @PreAuthorize("hasAuthority('SCOPE_S') or hasAuthority('SCOPE_A') or hasAuthority('SCOPE_T')")
  @GetMapping("/{id}")
  public ResponseEntity<AcademicQuestionsDto> getExamQuestionById(@PathVariable Integer id) {
    AcademicQuestionsDto examQuestion =
        academicQuestionsServiceimplementation.obtenerPreguntaPorId(id);
    return ResponseEntity.ok(examQuestion);
  }

  /**
   * Buscar preguntas por texto de la pregunta
   *
   * @param questionText Texto de la pregunta
   * @return Lista de preguntas encontradas
   */
  @PreAuthorize("hasAuthority('SCOPE_S') or hasAuthority('SCOPE_T')")
  @GetMapping("/search")
  public ResponseEntity<List<AcademicQuestionsDto>> getExamQuestionsByQuestionText(
      @RequestParam String questionText) {
    List<AcademicQuestionsDto> examQuestions =
        academicQuestionsServiceimplementation.obtenerPreguntasPorQuestionText(questionText);
    return ResponseEntity.ok(examQuestions);
  }

  /**
   * Elimina una pregunta por ID
   *
   * @param id ID de la pregunta
   * @return Respuesta de eliminación
   */
  @PreAuthorize("hasAuthority('SCOPE_S') or hasAuthority('SCOPE_T')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteExamQuestion(@PathVariable Integer id) {
    academicQuestionsServiceimplementation.eliminarPregunta(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Modificar una pregunta por ID
   *
   * @param id          ID de la pregunta
   * @param preguntaDto Pregunta a modificar
   * @return Pregunta modificada
   */
  // Modificar una pregunta por ID
  @PreAuthorize("hasAuthority('SCOPE_S') or hasAuthority('SCOPE_T')")
  @PutMapping("/{id}")
  public ResponseEntity<AcademicQuestionsDto> updateExamQuestion(@PathVariable Integer id,
      @RequestBody AcademicQuestionsDto preguntaDto) {
    AcademicQuestionsDto updatedQuestion =
        academicQuestionsServiceimplementation.modificarPregunta(id, preguntaDto);
    return ResponseEntity.ok(updatedQuestion);
  }

  // Manejar excepciones NoSuchElementException
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

}
