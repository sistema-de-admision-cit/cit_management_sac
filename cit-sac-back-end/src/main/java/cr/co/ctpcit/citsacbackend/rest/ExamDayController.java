package cr.co.ctpcit.citsacbackend.rest;

import cr.co.ctpcit.citsacbackend.logic.dto.dates.ExamDayDto;
import cr.co.ctpcit.citsacbackend.logic.services.ExamDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/ExamDays")
public class ExamDayController {

    ExamDayService examDayService;

    @Autowired
    public ExamDayController(ExamDayService examDayService) {
        this.examDayService = examDayService;
    }

    // Obtener todos los días de examen
    @GetMapping
    public ResponseEntity<List<ExamDayDto>> getAllExamDays() {
        List<ExamDayDto> examDays = examDayService.getAllExamDays();
        return ResponseEntity.ok(examDays);
    }

    // Crear un nuevo día de examen
    @PostMapping
    public ResponseEntity<ExamDayDto> createExamDay(@RequestBody ExamDayDto dto) {
        ExamDayDto createdDay = examDayService.createExamDay(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDay);
    }

    // Modificar un día de examen por ID
    @PutMapping("/{id}")
    public ResponseEntity<ExamDayDto> updateExamDay(@PathVariable Integer id, @RequestBody ExamDayDto dto) {
        ExamDayDto updatedDay = examDayService.updateExamDay(id, dto);
        return ResponseEntity.ok(updatedDay);
    }

    // Manejar excepciones NoSuchElementException
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}