package cr.co.ctpcit.citsacbackend.rest;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.AnswersDaiDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExamDaiController {

    @PostMapping("/answers")
    public ResponseEntity<?> createAnswersDai(@RequestBody List<AnswersDaiDto> answersDaiDto) {
        return ResponseEntity.ok().build();
    }

}
