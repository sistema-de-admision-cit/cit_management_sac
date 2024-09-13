package cr.co.ctpcit.citsacbackend.rest;

import cr.co.ctpcit.citsacbackend.data.entities.exams.academic.AcademicQuestionsEntity;
import cr.co.ctpcit.citsacbackend.logic.services.examsImplementations.AcademicQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/Academic")
public class ExamAcademicController {

    @Autowired
    private AcademicQuestionsService academicQuestionsService;

    @GetMapping
    public ResponseEntity<List<AcademicQuestionsEntity>> obtenerPreguntas() {
        try {
            List<AcademicQuestionsEntity> preguntas = academicQuestionsService.obtenerTodasLasPreguntas();
            return ResponseEntity.ok(preguntas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademicQuestionsEntity> modificarPregunta(
            @PathVariable Integer id,
            @RequestBody AcademicQuestionsEntity request) {
        try {
            AcademicQuestionsEntity preguntaModificada = academicQuestionsService.modificarPregunta(
                    id,
                    request.getQuestionText(),
                    request.getQuestionGrade(),
                    request.getImageUrl(),
                    request.getOptionA(),
                    request.getOptionB(),
                    request.getOptionC(),
                    request.getOptionD(),
                    request.getCorrectOption()
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
            academicQuestionsService.eliminarPregunta(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}