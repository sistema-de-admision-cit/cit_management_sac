package cr.co.ctpcit.citsacbackend.rest;

import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
import cr.co.ctpcit.citsacbackend.logic.services.InscriptionsService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inscriptions")
@Validated
public class InscriptionsController {
    InscriptionsService inscriptionsService;

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
     * This method creates a new inscription in the database following RFC 9110 as
     * the official Request for Comments for HTTP Semantics and Content.
     * @return a response entity with the status code and Location header
     */
    @PostMapping("/add")
    public ResponseEntity<Void> createInscription(@RequestBody @Valid StudentDto student, UriComponentsBuilder ucb) {
        String id = inscriptionsService.addInscription(student).idNumber();
        URI location = ucb.path("/api/inscriptions/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * Handle validation exceptions
     * @param e the exception
     * @return a map with the errors
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    /**
     * Handle constraint violation exceptions
     * @param e the exception
     * @return a response entity with the error message
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
