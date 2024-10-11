package cr.co.ctpcit.citsacbackend.rest.config;

import cr.co.ctpcit.citsacbackend.logic.dto.dates.ConfigDateDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.dates.ExamPeriodDto;
import cr.co.ctpcit.citsacbackend.logic.services.config.ExamPeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/exam-periods")
public class ExamPeriodController {

  ExamPeriodService examPeriodService;

  @Autowired
  public ExamPeriodController(ExamPeriodService examPeriodService) {
    this.examPeriodService = examPeriodService;
  }

  // Obtener todos los periodos de examen
  @PreAuthorize("hasAuthority('SCOPE_S')")
  @GetMapping
  public ResponseEntity<List<ExamPeriodDto>> getAllExamPeriods() {
    List<ExamPeriodDto> examPeriods = examPeriodService.getAllExamPeriods();
    return ResponseEntity.ok(examPeriods);
  }

  // Crear un nuevo periodo de examen
  @PreAuthorize("hasAuthority('SCOPE_S')")
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
  @PreAuthorize("hasAuthority('SCOPE_S')")
  @PutMapping("/{id}")
  public ResponseEntity<ExamPeriodDto> updateExamPeriod(@PathVariable Integer id,
      @RequestBody ExamPeriodDto dto) {
    ExamPeriodDto updatedPeriod = examPeriodService.updateExamPeriod(id, dto);
    return ResponseEntity.ok(updatedPeriod);
  }

  /**
   * Agregar días de aplicación de examen
   *
   * @param dto ConfigDateDTO
   * @return ResponseEntity<ExamPeriodDto>
   */
  @PreAuthorize("hasAuthority('SCOPE_S')")
  @PostMapping("/save-exam-schedule")
  public ResponseEntity<ExamPeriodDto> addExamApplicationDays(@RequestBody ConfigDateDTO dto) {
    Boolean added = false;
    try {
      added = examPeriodService.addExamApplicationDays(dto);
    } catch (Exception e) {
      ResponseEntity.badRequest().body(e.getMessage());
    }

    return added ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
  }

  // Manejar excepciones NoSuchElementException
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }
}
