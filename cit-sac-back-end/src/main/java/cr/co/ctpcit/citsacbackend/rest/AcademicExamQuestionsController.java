package cr.co.ctpcit.citsacbackend.rest;

import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.AcademicExamQuestionsDto;
import cr.co.ctpcit.citsacbackend.logic.services.AcademicExamQuestionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academic-exams")
@RequiredArgsConstructor
public class AcademicExamQuestionsController {

    private final AcademicExamQuestionsService service;

    @GetMapping("/{examId}/questions")
    public ResponseEntity<List<AcademicExamQuestionsDto>> getAcademicExamQuestions(@PathVariable Integer examId) {
        List<AcademicExamQuestionsDto> examAnswers = service.getExamAnswers(examId);
        return ResponseEntity.ok(examAnswers);
    }
}
