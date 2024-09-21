package cr.co.ctpcit.citsacbackend.rest;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiExamQuestionsDto;
import cr.co.ctpcit.citsacbackend.logic.services.DaiExamQuestionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class DaiExamQuestionsController {

    private final DaiExamQuestionsService service;

    @GetMapping("/{examId}/questions")
    public ResponseEntity<List<DaiExamQuestionsDto>> getExamQuestions(@PathVariable Integer examId) {
        List<DaiExamQuestionsDto> examAnswers = service.getExamAnswers(examId);
        return ResponseEntity.ok(examAnswers);
    }
}