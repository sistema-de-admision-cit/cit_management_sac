package cr.co.ctpcit.citsacbackend.rest.inscriptions;


import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
import cr.co.ctpcit.citsacbackend.logic.exceptions.EnrollmentException;
import cr.co.ctpcit.citsacbackend.logic.exceptions.StorageFileNotFoundException;
import cr.co.ctpcit.citsacbackend.logic.services.inscriptions.InscriptionsService;
import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/inscription")
@CrossOrigin("*")
@Validated
public class InscriptionFormController {
    private final InscriptionsService inscriptionsService;
    private final StorageService storageService;

    @Autowired
    public InscriptionFormController(InscriptionsService inscriptionsService,
                                     StorageService storageService) {
        this.inscriptionsService = inscriptionsService;
        this.storageService = storageService;
    }

    /**
     * This method creates a new inscription in the database following RFC 9110 as
     * the official Request for Comments for HTTP Semantics and Content.
     * @return a response entity with the status code and Location header
     */
    @PostMapping(path = "/add", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Void> createInscription(@ModelAttribute StudentDto studentDto,
                                                  MultipartFile file, UriComponentsBuilder uriComponentsBuilder) {
        return null;
    }

    /**
     * Handle file not found exceptions
     * @param exc the exception
     * @return a response entity with the status code
     */
    @ExceptionHandler(StorageFileNotFoundException.class)
    ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
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
