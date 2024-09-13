package cr.co.ctpcit.citsacbackend.rest;
import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiQuestionsEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.AnswersDaiDto;
import cr.co.ctpcit.citsacbackend.logic.services.examsImplementations.DaiQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/Dai")
public class ExamDaiController {

    @Autowired
    private DaiQuestionsService daiQuestionsService;

    @PostMapping("/answers")
    public ResponseEntity<?> createAnswersDai(@RequestBody List<AnswersDaiDto> answersDaiDto) {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<DaiQuestionsEntity>> obtenerTodasLasPreguntas() {
        List<DaiQuestionsEntity> preguntas = daiQuestionsService.obtenerTodasLasPreguntas();
        return ResponseEntity.ok(preguntas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DaiQuestionsEntity> obtenerPreguntaPorId(@PathVariable Integer id) {
        try {
            DaiQuestionsEntity pregunta = daiQuestionsService.obtenerPreguntaPorId(id);
            return ResponseEntity.ok(pregunta);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DaiQuestionsEntity> modificarPregunta(
            @PathVariable Integer id,
            @RequestBody DaiQuestionsEntity request) {
        try {
            DaiQuestionsEntity preguntaModificada = daiQuestionsService.modificarPregunta(
                    id,
                    request.getQuestionText(),
                    request.getQuestionGrade(),
                    request.getImageUrl()
            );
            return ResponseEntity.ok(preguntaModificada);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPregunta(@PathVariable Integer id) {
        try {
            daiQuestionsService.eliminarPregunta(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
