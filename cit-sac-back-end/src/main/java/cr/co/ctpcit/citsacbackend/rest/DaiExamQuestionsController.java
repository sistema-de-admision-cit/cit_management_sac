package cr.co.ctpcit.citsacbackend.rest;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiExamQuestionsDto;
import cr.co.ctpcit.citsacbackend.logic.services.DaiExamQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class DaiExamQuestionsController {

    private final DaiExamQuestionsService examQuestionsService;

    @Autowired
    public DaiExamQuestionsController(DaiExamQuestionsService examQuestionsService) {
        this.examQuestionsService = examQuestionsService;
    }

    // Obtener todas las preguntas de examen
    @GetMapping
    public ResponseEntity<List<DaiExamQuestionsDto>> getAllExamQuestions() {
        List<DaiExamQuestionsDto> examQuestions = examQuestionsService.getAllExamQuestions();
        return ResponseEntity.ok(examQuestions);
    }

    // Agregar una nueva pregunta de examen
    @PostMapping
    public ResponseEntity<DaiExamQuestionsDto> addExamQuestion(@Validated @RequestBody DaiExamQuestionsDto examQuestionDto) {
        DaiExamQuestionsDto createdExamQuestion = examQuestionsService.addExamQuestion(examQuestionDto);
        return ResponseEntity.ok(createdExamQuestion);
    }
}