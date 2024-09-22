package cr.co.ctpcit.citsacbackend.rest.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
import cr.co.ctpcit.citsacbackend.logic.services.inscriptions.InscriptionsService;
import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/inscriptions")
@Validated
public class InscriptionsController {
  private final InscriptionsService inscriptionsService;
  private final StorageService storageService;

  @Autowired
  public InscriptionsController(InscriptionsService inscriptionsService,
      StorageService storageService) {
    this.inscriptionsService = inscriptionsService;
    this.storageService = storageService;
  }

  /**
   * Get an inscription by id
   *
   * @param id the id of the student
   * @return the inscription with the given id
   */
  @GetMapping("/{id}")
  public ResponseEntity<StudentDto> getInscriptionById(@PathVariable("id") Long id) {
    StudentDto student = inscriptionsService.findStudentById(id);
    return student == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(student);
  }

  /**
   * Get an inscription by value
   *
   * @param value the name of the student
   * @return the inscription with the given name
   */
  @GetMapping("/search")
  public ResponseEntity<Iterable<StudentDto>> getInscriptionsByValue(
      @NotNull @RequestParam String value) {
    List<StudentDto> student = inscriptionsService.findStudentByValue(value);
    return student == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(student);
  }

  /**
   * Get all inscriptions
   *
   * @return a list of all inscriptions
   */
  @GetMapping
  public ResponseEntity<Iterable<StudentDto>> getInscriptions(
      @PageableDefault(page = 0, size = 25) Pageable pageable) {
    List<StudentDto> inscriptions = inscriptionsService.getAllInscriptions(pageable);
    return inscriptions.isEmpty() ?
        ResponseEntity.noContent().build() :
        ResponseEntity.ok(inscriptions);
  }

  /**
   * Update exam date
   *
   * @param id   the id of the enrollment
   * @param date the new date of the exam
   * @return the updated enrollment
   */
  @PutMapping("/{id}/exam")
  public ResponseEntity<StudentDto> updateExamDate(@PathVariable("id") String id, @NotNull @NotEmpty
  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$",
      message = "Formato de fecha incorrecto. Use yyyy-MM-dd") @RequestParam String date) {
    StudentDto student = inscriptionsService.updateExamDate(id, date);
    return student == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(student);
  }

  /**
   * @param id     the id of the enrollment
   * @param status the new status of the enrollment
   * @return ok if the status was updated, not found otherwise
   */
  @PutMapping("/update-status/{id}")
  public ResponseEntity<StudentDto> updateStatus(@PathVariable("id") Long id,
      @RequestParam @NotNull ProcessStatus status) {
    boolean updated = inscriptionsService.changeStatus(id, status);
    return updated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
  }

  /**
   * Change whatsapp notification permission
   *
   * @param id         the id of the student
   * @param permission the new permission
   * @return ok if the permission was updated, not found otherwise
   */
  @PutMapping("/update-whatsapp/{id}")
  public ResponseEntity<StudentDto> changeWhatsappPermission(@PathVariable("id") Long id,
      @RequestParam @NotNull Boolean permission) {
    boolean updated = inscriptionsService.changeWhatsappPermission(id, permission);
    return updated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
  }

  @PutMapping("/update-enrollment/{enrollmentId}")
  public ResponseEntity<String> updateEnrollment(@PathVariable("enrollmentId") Long enrollmentId,
      @RequestParam @NotNull ProcessStatus status, @RequestParam @NotNull String examDate,
      @RequestParam @NotNull String whatsappPermission, @RequestParam String comment,
      @RequestParam Integer changedBy) {
    Boolean updated =
        inscriptionsService.updateEnrollment(enrollmentId, status, examDate, whatsappPermission,
            comment, changedBy);

    return updated ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

  /**
   * Download documents
   *
   * @param filename the name of the document
   * @return the document as a resource
   */
  @GetMapping("/documents/download/{filename}")
  public ResponseEntity<Resource> downloadDocuments(@PathVariable String filename) {
    Resource resource = storageService.loadAsResource(filename);
    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
  }

  /**
   * Delete a document
   *
   * @param id       the id of the document
   * @param filename the path of the document in the static/files document folder
   * @return ok if the document was deleted, not found otherwise
   */
  @DeleteMapping("/documents/delete/{id}")
  public ResponseEntity<String> deleteDocument(@PathVariable Long id,
      @RequestParam String filename) {
    boolean databaseDeleted = inscriptionsService.deleteDocument(id);

    boolean storageDeleted = storageService.deleteDocument(filename);

    return databaseDeleted && storageDeleted ?
        ResponseEntity.ok().build() :
        ResponseEntity.notFound().build();
  }

  /**
   * Upload a document
   *
   * @param file
   * @param documentName
   * @param documentType
   * @param enrollmentId
   * @return
   */
  @PostMapping("/documents/upload")
  public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file,
      @RequestParam("documentName") String documentName,
      @RequestParam("documentType") String documentType,
      @RequestParam("enrollmentId") Long enrollmentId) {
    if (file.isEmpty()) {
      return ResponseEntity.badRequest().body("No se seleccionó ningún archivo para subir.");
    }
    storageService.store(file, documentName);

    inscriptionsService.saveDocument(documentName, documentType, enrollmentId);

    return ResponseEntity.ok().build();
  }

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
}
