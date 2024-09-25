package cr.co.ctpcit.citsacbackend.rest.inscriptions;

import cr.co.ctpcit.citsacbackend.logic.dto.inscription.EnrollmentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
import cr.co.ctpcit.citsacbackend.logic.exceptions.EnrollmentException;
import cr.co.ctpcit.citsacbackend.logic.exceptions.StorageFileNotFoundException;
import cr.co.ctpcit.citsacbackend.logic.services.inscriptions.InscriptionsService;
import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

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
   * This method creates a new inscription in the database following RFC 9110 as the official
   * Request for Comments for HTTP Semantics and Content.
   *
   * @return a response entity with the status code and Location header
   */
  @PostMapping(path = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  @Async
  public CompletableFuture<ResponseEntity<Void>> createInscription(
      @RequestPart("inscription") StudentDto inscription,
      @RequestPart("grades") MultipartFile grades,
      @RequestPart(name = "letter", required = false) MultipartFile letter,
      UriComponentsBuilder uriComponentsBuilder) {
    //Save inscription
    EnrollmentDto enrollment = inscriptionsService.addInscription(inscription, grades, letter);

    //Return created status and location header
    return CompletableFuture.completedFuture(ResponseEntity.created(
            uriComponentsBuilder.path("/api/inscription/{id}").buildAndExpand(enrollment.id()).toUri())
        .build());
  }

  /**
   * Handle file not found exceptions
   *
   * @param exc the exception
   * @return a response entity with the status code
   */
  @ExceptionHandler(StorageFileNotFoundException.class)
  ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
    return ResponseEntity.notFound().build();
  }

  /**
   * Handle Enrollment exceptions
   *
   * @param e the exception
   * @return a response entity with the error message
   */
  @ExceptionHandler(EnrollmentException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  ResponseEntity<String> handleEnrollmentException(EnrollmentException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
  }

  /**
   * Handle not found exceptions
   *
   * @param exc the exception
   * @return a response entity with the status code
   */
  @ExceptionHandler({NoSuchElementException.class, StorageFileNotFoundException.class})
  ResponseEntity<?> handleNoSuchElementException(NoSuchElementException exc) {
    return ResponseEntity.notFound().build();
  }

  /**
   * Handle storage exceptions
   *
   * @param e the exception
   * @return a response entity with the error message
   */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException e) {
        return new ResponseEntity<>("El archivo no puede ser mayor a 5MB", HttpStatus.PAYLOAD_TOO_LARGE);
    }
}
