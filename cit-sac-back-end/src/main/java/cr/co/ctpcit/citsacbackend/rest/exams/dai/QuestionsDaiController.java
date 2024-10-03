package cr.co.ctpcit.citsacbackend.rest.exams.dai;

import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiQuestionsDto;
import cr.co.ctpcit.citsacbackend.logic.services.examsImplementations.DaiQuestionsServiceImplementation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/questions-dai")
public class QuestionsDaiController {
  private final DaiQuestionsServiceImplementation daiQuestionsServiceimplementation;

  // Obtener todas las preguntas de examen

  /**
   * Obtiene todas las preguntas de examen DAI.
   *
   * @return ResponseEntity con la lista de preguntas de examen DAI
   */
  @PreAuthorize("hasAuthority('SCOPE_S') or hasAuthority('SCOPE_P')")
  @GetMapping
  //TODO: Implementar paginación en la respuesta
  public ResponseEntity<List<DaiQuestionsDto>> getAllExamQuestions() {
    List<DaiQuestionsDto> examQuestions =
        daiQuestionsServiceimplementation.obtenerTodasLasPreguntas();
    return ResponseEntity.ok(examQuestions);
  }

  // Obtener una pregunta por ID

  /**
   * Obtiene una pregunta de examen DAI por ID.
   *
   * @param id ID de la pregunta
   * @return ResponseEntity con la pregunta de examen DAI
   */
  @PreAuthorize("hasAuthority('SCOPE_S') or hasAuthority('SCOPE_P')")
  @GetMapping("/{id}")
  public ResponseEntity<DaiQuestionsDto> getExamQuestionById(@PathVariable Integer id) {
    DaiQuestionsDto examQuestion = daiQuestionsServiceimplementation.obtenerPreguntaPorId(id);
    return ResponseEntity.ok(examQuestion);
  }

  // Buscar preguntas por texto de la pregunta

  /**
   * Obtiene una lista de preguntas de examen DAI que contienen el texto de la pregunta.
   *
   * @param questionText Texto de la pregunta
   * @return ResponseEntity con la lista de preguntas de examen DAI
   */
  @PreAuthorize("hasAuthority('SCOPE_S') or hasAuthority('SCOPE_P')")
  @GetMapping("/search")
  public ResponseEntity<List<DaiQuestionsDto>> getExamQuestionsByQuestionText(
      @RequestParam String questionText) {
    List<DaiQuestionsDto> examQuestions =
        daiQuestionsServiceimplementation.obtenerPreguntasPorQuestionText(questionText);
    return ResponseEntity.ok(examQuestions);
  }

  /**
   * Elimina una pregunta de examen DAI por ID.
   *
   * @param id ID de la pregunta
   * @return ResponseEntity con status 204
   */
  @PreAuthorize("hasAuthority('SCOPE_S') or hasAuthority('SCOPE_P')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteExamQuestion(@PathVariable Integer id) {
    daiQuestionsServiceimplementation.eliminarPregunta(id);
    return ResponseEntity.noContent().build();
  }

  // Modificar una pregunta por ID

  /**
   * Modifica una pregunta de examen DAI por ID.
   *
   * @param id          ID de la pregunta
   * @param preguntaDto DTO con los datos de la pregunta a modificar
   * @return ResponseEntity con la pregunta de examen DAI modificada
   */
  @PreAuthorize("hasAuthority('SCOPE_S') or hasAuthority('SCOPE_P')")
  @PutMapping("/{id}")
  public ResponseEntity<DaiQuestionsDto> updateExamQuestion(@PathVariable Integer id,
      @RequestBody DaiQuestionsDto preguntaDto) {
    DaiQuestionsDto updatedQuestion =
        daiQuestionsServiceimplementation.modificarPregunta(id, preguntaDto);
    return ResponseEntity.ok(updatedQuestion);
  }

  // Manejar excepciones NoSuchElementException
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }



}
