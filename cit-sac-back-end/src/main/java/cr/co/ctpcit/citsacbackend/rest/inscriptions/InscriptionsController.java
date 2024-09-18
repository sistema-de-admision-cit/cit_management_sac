package cr.co.ctpcit.citsacbackend.rest.inscriptions;

import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
import cr.co.ctpcit.citsacbackend.logic.exceptions.EnrollmentException;
import cr.co.ctpcit.citsacbackend.logic.services.inscriptions.InscriptionsService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscriptions")
@Validated
public class InscriptionsController {
    private final InscriptionsService inscriptionsService;

    @Autowired
    public InscriptionsController(InscriptionsService inscriptionsService) {
        this.inscriptionsService = inscriptionsService;
    }

    /**
     * Get an inscription by id
     * @param id the id of the inscription
     * @return the inscription with the given id
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getInscriptionById(@NotBlank
                                                             @Pattern(regexp = "^[0-9]*$", message = "el id debe ser numérico")
                                                             @Size(min = 9, max = 20, message = "el tamaño del id debe ser entre 9 y 20 caracteres")
                                                             @PathVariable("id") String id) {
        StudentDto student = inscriptionsService.findStudentByIdNumber(id);
        return student == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(student);
    }

    /**
     * Get an inscription by value
     * @param value the name of the student
     * @return the inscription with the given name
     */
    @GetMapping("/search")
    public ResponseEntity<Iterable<StudentDto>> getInscriptionsByValue(@NotNull @RequestParam String value) {
        List<StudentDto> student = inscriptionsService.findStudentByValue(value);
        return student == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(student);
    }

    /**
     * Get all inscriptions
     * @return a list of all inscriptions
     */
    @GetMapping
    public ResponseEntity<Iterable<StudentDto>> getInscriptions(@PageableDefault(page = 0, size = 25) Pageable pageable) {
        List<StudentDto> inscriptions = inscriptionsService.getAllInscriptions(pageable);
        return inscriptions.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(inscriptions);
    }

    /**
     * Handle constraint violation exceptions
     * @param e the exception
     * @return a response entity with the error message
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle Enrollment exceptions
     * @param e the exception
     * @return a response entity with the error message
     */
    @ExceptionHandler(EnrollmentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ResponseEntity<String> handleEnrollmentException(EnrollmentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
