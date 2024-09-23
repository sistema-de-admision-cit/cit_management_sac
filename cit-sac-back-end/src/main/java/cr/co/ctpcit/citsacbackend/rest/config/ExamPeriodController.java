package cr.co.ctpcit.citsacbackend.rest.config;

import cr.co.ctpcit.citsacbackend.logic.dto.dates.ExamPeriodDto;
import cr.co.ctpcit.citsacbackend.logic.services.config.ExamPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/ExamPeriods")
public class ExamPeriodController {

  ExamPeriodService examPeriodService;

  @Autowired
  public ExamPeriodController(ExamPeriodService examPeriodService) {
    this.examPeriodService = examPeriodService;
  }

  // Obtener todos los periodos de examen
  @GetMapping
  public ResponseEntity<List<ExamPeriodDto>> getAllExamPeriods() {
    List<ExamPeriodDto> examPeriods = examPeriodService.getAllExamPeriods();
    return ResponseEntity.ok(examPeriods);
  }

  // Crear un nuevo periodo de examen
  @PostMapping
  public ResponseEntity<ExamPeriodDto> createExamPeriod(@RequestBody ExamPeriodDto dto) {
    ExamPeriodDto createdPeriod = examPeriodService.createExamPeriod(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdPeriod);
  }

  //    // Obtener un periodo de examen por ID
  //    @GetMapping("/{id}")
  //    public ResponseEntity<ExamPeriodDto> getExamPeriodById(@PathVariable Integer id) {
  //        ExamPeriodDto examPeriod = examPeriodService.getExamPeriodById(id);
  //        return ResponseEntity.ok(examPeriod);
  //    }

  // Modificar un periodo de examen por ID
  @PutMapping("/{id}")
  public ResponseEntity<ExamPeriodDto> updateExamPeriod(@PathVariable Integer id,
      @RequestBody ExamPeriodDto dto) {
    ExamPeriodDto updatedPeriod = examPeriodService.updateExamPeriod(id, dto);
    return ResponseEntity.ok(updatedPeriod);
  }

  // Manejar excepciones NoSuchElementException
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }
}
