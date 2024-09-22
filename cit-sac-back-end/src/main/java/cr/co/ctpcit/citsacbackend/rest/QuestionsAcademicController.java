package cr.co.ctpcit.citsacbackend.rest;

import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.AcademicQuestionsDto;
import cr.co.ctpcit.citsacbackend.logic.services.examsImplementations.AcademicQuestionsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/Academic")
public class QuestionsAcademicController {

  @Autowired
  private final AcademicQuestionsServiceImplementation academicQuestionsServiceimplementation;

  @Autowired
  public QuestionsAcademicController(AcademicQuestionsServiceImplementation examQuestionsService) {
    this.academicQuestionsServiceimplementation = examQuestionsService;
  }

  // Obtener todas las preguntas de examen
  @GetMapping
  public ResponseEntity<List<AcademicQuestionsDto>> getAllExamQuestions() {
    List<AcademicQuestionsDto> examQuestions =
        academicQuestionsServiceimplementation.obtenerTodasLasPreguntas();
    return ResponseEntity.ok(examQuestions);
  }

  // Obtener una pregunta por ID
  @GetMapping("/{id}")
  public ResponseEntity<AcademicQuestionsDto> getExamQuestionById(@PathVariable Integer id) {
    AcademicQuestionsDto examQuestion =
        academicQuestionsServiceimplementation.obtenerPreguntaPorId(id);
    return ResponseEntity.ok(examQuestion);
  }

  // Buscar preguntas por texto de la pregunta
  @GetMapping("/search")
  public ResponseEntity<List<AcademicQuestionsDto>> getExamQuestionsByQuestionText(
      @RequestParam String questionText) {
    List<AcademicQuestionsDto> examQuestions =
        academicQuestionsServiceimplementation.obtenerPreguntasPorQuestionText(questionText);
    return ResponseEntity.ok(examQuestions);
  }


  // Eliminar una pregunta por ID
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteExamQuestion(@PathVariable Integer id) {
    academicQuestionsServiceimplementation.eliminarPregunta(id);
    return ResponseEntity.noContent().build();
  }

  // Modificar una pregunta por ID
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
