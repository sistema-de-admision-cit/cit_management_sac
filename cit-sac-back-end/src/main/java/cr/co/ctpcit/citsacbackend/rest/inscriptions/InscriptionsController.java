package cr.co.ctpcit.citsacbackend.rest.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentUpdateDto;
import cr.co.ctpcit.citsacbackend.logic.exceptions.StorageException;
import cr.co.ctpcit.citsacbackend.logic.services.inscriptions.InscriptionsService;
import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/inscriptions")
@Validated
public class InscriptionsController {
  private final InscriptionsService inscriptionsService;
  private final StorageService storageService;

  /**
   * Get an inscription by id
   *
   * @param idNumber the id of the student
   * @return the inscription with the given id
   */
  @GetMapping("/{id}")
  public ResponseEntity<Iterable<EnrollmentDto>> getEnrollmentsByStudentId(
      @PathVariable("id") String idNumber) {
    Iterable<EnrollmentDto> enrollments = inscriptionsService.findEnrollmentsByStudentId(idNumber);

    return enrollments == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(enrollments);
  }

  /**
   * Get an inscription by value
   *
   * @param value the name of the student
   * @return the inscription with the given name
   */
  @GetMapping("/search")
  public ResponseEntity<Iterable<EnrollmentDto>> getInscriptionsByValue(
      @NotNull @RequestParam String value) {
    List<EnrollmentDto> enrollments = inscriptionsService.findStudentByValue(value);

    return enrollments == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(enrollments);
  }

  /**
   * Get all inscriptions
   *
   * @return a list of all inscriptions
   */
  @GetMapping
  public ResponseEntity<Iterable<EnrollmentDto>> getInscriptions(
      @PageableDefault(page = 0, size = 25) Pageable pageable) {
    List<EnrollmentDto> inscriptions = inscriptionsService.getAllInscriptions(pageable);

    return inscriptions.isEmpty() ?
        ResponseEntity.noContent().build() :
        ResponseEntity.ok(inscriptions);
  }

  /**
   * Update exam date
   * @param id the id of the enrollment
   */
  @PutMapping("/update-exam-date/{id}")
  public ResponseEntity<Void> updateExamDate(@PathVariable("id") String id,
      @RequestBody EnrollmentUpdateDto enrollmentUpdate) {
    inscriptionsService.updateExamDate(id, enrollmentUpdate);

    return ResponseEntity.noContent().build();
  }

  /**
   * Update process status
   * @param id the id of the enrollment
   */
  @PutMapping("/update-status/{id}")
  public ResponseEntity<Void> updateStatus(@PathVariable("id") String id,
      @RequestBody EnrollmentUpdateDto enrollmentUpdate) {
    inscriptionsService.updateProcessStatus(id, enrollmentUpdate);

    return ResponseEntity.noContent().build();
  }

  /**
   * Change whatsapp notification permission
   *
   * @param id the id of the student
   */
  @PutMapping("/update-whatsapp/{id}")
  public ResponseEntity<Void> changeWhatsappPermission(@PathVariable("id") String id,
      @RequestBody EnrollmentUpdateDto enrollmentUpdate) {
    inscriptionsService.updateWhatsappPermission(id, enrollmentUpdate);

    return ResponseEntity.noContent().build();
  }

  /**
   * Update enrollment
   *
   * @param enrollmentId       the id of the enrollment
   */
  @PutMapping("/update-enrollment/{enrollmentId}")
  public ResponseEntity<String> updateEnrollment(@PathVariable("enrollmentId") String enrollmentId,
      @RequestBody EnrollmentUpdateDto enrollmentUpdate) {
    inscriptionsService.updateEnrollment(enrollmentId, enrollmentUpdate);

    return ResponseEntity.noContent().build();
  }

  /**
   * Download documents
   *
   * @param filename the name of the document
   * @return the document as a resource
   */
  /*@GetMapping("/documents/download/{filename}")
  public ResponseEntity<Resource> downloadDocuments(@PathVariable String filename) {
    Resource resource;

    try {
      resource = storageService.loadAsResource(filename);
    } catch (Exception e) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
  }*/

  /**
   * Delete a document
   *
   * @param id       the id of the document
   * @param filename the path of the document in the static/files document folder
   * @return ok if the document was deleted, not found otherwise
   */
  /*@DeleteMapping("/documents/delete/{id}")
  public ResponseEntity<String> deleteDocument(@PathVariable Long id,
      @RequestParam String filename) {
    boolean databaseDeleted = inscriptionsService.deleteDocument(id);

    boolean storageDeleted = storageService.deleteDocument(filename);

    return databaseDeleted && storageDeleted ?
        ResponseEntity.ok().build() :
        ResponseEntity.notFound().build();
  }*/

  /**
   * Upload a document
   *
   * @param file         the file to upload
   * @param documentName the name of the document
   * @param documentType the type of the document
   * @param enrollmentId the id of the enrollment
   * @return the document
   */
  /*@PostMapping("/documents/upload")
  @Async
  public CompletableFuture<ResponseEntity<DocumentDto>> uploadDocument(
      @RequestParam("file") MultipartFile file, @RequestParam("documentName") String documentName,
      @RequestParam("documentType") String documentType,
      @RequestParam("enrollmentId") Long enrollmentId) {
    if (file.isEmpty()) {
      return CompletableFuture.completedFuture(ResponseEntity.badRequest().build());
    }
    DocumentDto document =
        inscriptionsService.saveDocument(documentName, documentType, enrollmentId);

    storageService.store(file, documentName);

    return CompletableFuture.completedFuture(ResponseEntity.ok(document));
  }*/

  /**
   * Handle constraint violation exceptions
   *
   * @param e the exception
   * @return a response entity with the error message
   */
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  /**
   * Handle not found exceptions
   *
   * @param exc the exception
   * @return a response entity with the status code
   */
  @ExceptionHandler({NoSuchElementException.class, StorageException.class})
  ResponseEntity<?> handleNoSuchElementException(NoSuchElementException exc) {
    return ResponseEntity.notFound().build();
  }
}
