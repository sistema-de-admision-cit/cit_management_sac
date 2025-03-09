package cr.co.ctpcit.citsacbackend.rest.exam;

import cr.co.ctpcit.citsacbackend.logic.dto.exam.ExamDto;
import cr.co.ctpcit.citsacbackend.logic.services.exam.ExamServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exams")
public class ExamController {
    private final ExamServiceImpl examService;

    public ExamController(ExamServiceImpl examService) {
        this.examService = examService;
    }

    @PostMapping("/create")
    public ResponseEntity<ExamDto> createExam(@RequestBody ExamDto examDto) {
        try {
            ExamDto savedExam = examService.createExam(examDto);
            return ResponseEntity.ok(savedExam);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<ExamDto>> getAllExams(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ExamDto> examsPage = examService.getExams(pageable);
        return ResponseEntity.ok(examsPage);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<ExamDto> getExamById(@PathVariable Long id) {
        ExamDto exam = examService.getExamById(id);
        return exam != null ? ResponseEntity.ok(exam) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update")
    public ResponseEntity<ExamDto> updateExam(@RequestBody ExamDto examDto) {
        try {
            return ResponseEntity.ok(examService.updateExam(examDto));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteExam(@PathVariable Long id) {
        try {
            examService.deleteExam(id);
            return ResponseEntity.ok("Exam deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Exams service is up and running.");
    }
}
