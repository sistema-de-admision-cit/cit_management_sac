package cr.co.ctpcit.citsacbackend.rest.inscriptions;

import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentDto;
import cr.co.ctpcit.citsacbackend.logic.exceptions.EnrollmentException;
import cr.co.ctpcit.citsacbackend.logic.exceptions.StorageFileNotFoundException;
import cr.co.ctpcit.citsacbackend.logic.services.inscriptions.InscriptionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static cr.co.ctpcit.citsacbackend.rest.inscriptions.InscriptionsFileVerifier.verifyFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inscription")
@CrossOrigin("*")
@Validated
public class InscriptionFormController {

  private final InscriptionsService inscriptionsService;

  /**
   * This method creates a new inscription based on the information provided in the request from the
   * ctp cit webpage inscription form.
   *
   * @return a response entity with the status code and Location header
   */
  @PostMapping(path = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<Void> createInscription(
      @RequestPart("inscription") EnrollmentDto inscription,
      @RequestPart(value = "grades", required = false) MultipartFile grades,
      @RequestPart(name = "letter", required = false) MultipartFile letter,
      UriComponentsBuilder uriComponentsBuilder) {
    verifyFile(grades);
    verifyFile(letter);

    EnrollmentDto enrolled = inscriptionsService.addInscription(inscription, grades, letter);

    //Return created status and location header
    return ResponseEntity.created(
            uriComponentsBuilder.path("/api/inscription/{id}").buildAndExpand(enrolled.id()).toUri())
        .build();
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
  @ExceptionHandler({EnrollmentException.class, ExecutionException.class})
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
  @ExceptionHandler({NoSuchElementException.class})
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
    return new ResponseEntity<>("El archivo no puede ser mayor a 5MB",
        HttpStatus.PAYLOAD_TOO_LARGE);
  }
}
