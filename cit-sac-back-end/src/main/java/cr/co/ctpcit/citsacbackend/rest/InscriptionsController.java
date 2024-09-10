package cr.co.ctpcit.citsacbackend.rest;

import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
import cr.co.ctpcit.citsacbackend.logic.services.InscriptionsService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
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
    @GetMapping("/inscriptions/{id}")
    public ResponseEntity<StudentDto> getInscriptionById(@NotBlank
                                                             @Size(min = 9, max = 20, message = "el tamaño del id debe ser entre 9 y 20 caracteres")
                                                             @PathVariable("id") String id) {
        return ResponseEntity.ok(inscriptionsService.findStudentByIdNumber(id));
    }

    /**
     * Get all inscriptions
     * @return a list of all inscriptions
     */
    @GetMapping("/inscriptions")
    public ResponseEntity<Iterable<StudentDto>> getInscriptions() {
        return ResponseEntity.ok(inscriptionsService.getAllInscriptions());
    }

    //TODO: Implement inscriptions post endpoint to create a new inscription
    @PostMapping("/inscriptions/add")
    public ResponseEntity<?> createInscription() {
        return ResponseEntity.ok().build();
    }

    /**
     * Handle validation exceptions
     * @param ex
     * @return a map with the errors
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
