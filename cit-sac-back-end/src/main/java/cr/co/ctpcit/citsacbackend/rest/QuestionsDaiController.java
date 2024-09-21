package cr.co.ctpcit.citsacbackend.rest;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiQuestionsDto;
import cr.co.ctpcit.citsacbackend.logic.services.examsImplementations.DaiQuestionsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/Dai")
public class QuestionsDaiController {

    @Autowired
    private final DaiQuestionsServiceImplementation daiQuestionsServiceimplementation;

    @Autowired
    public QuestionsDaiController(DaiQuestionsServiceImplementation examQuestionsService) {
        this.daiQuestionsServiceimplementation = examQuestionsService;
    }

    // Obtener todas las preguntas de examen
    @GetMapping
    public ResponseEntity<List<DaiQuestionsDto>> getAllExamQuestions() {
        List<DaiQuestionsDto> examQuestions = daiQuestionsServiceimplementation.obtenerTodasLasPreguntas();
        return ResponseEntity.ok(examQuestions);
    }

    // Obtener una pregunta por ID
    @GetMapping("/{id}")
    public ResponseEntity<DaiQuestionsDto> getExamQuestionById(@PathVariable Integer id) {
        DaiQuestionsDto examQuestion = daiQuestionsServiceimplementation.obtenerPreguntaPorId(id);
        return ResponseEntity.ok(examQuestion);
    }

    // Eliminar una pregunta por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamQuestion(@PathVariable Integer id) {
        daiQuestionsServiceimplementation.eliminarPregunta(id);
        return ResponseEntity.noContent().build();
    }

    // Modificar una pregunta por ID
    @PutMapping("/{id}")
    public ResponseEntity<DaiQuestionsDto> updateExamQuestion(@PathVariable Integer id, @RequestBody DaiQuestionsDto preguntaDto) {
        DaiQuestionsDto updatedQuestion = daiQuestionsServiceimplementation.modificarPregunta(id, preguntaDto);
        return ResponseEntity.ok(updatedQuestion);
    }

    // Manejar excepciones NoSuchElementException
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }



}
