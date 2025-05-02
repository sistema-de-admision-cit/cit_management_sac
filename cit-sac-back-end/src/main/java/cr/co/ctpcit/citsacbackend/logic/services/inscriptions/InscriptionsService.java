package cr.co.ctpcit.citsacbackend.logic.services.inscriptions;

import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.DocumentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentUpdateDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
/**
 * Service interface for managing student enrollments and related documents.
 * Provides operations for enrollment management, status updates, and document handling.
 */
@Service
public interface InscriptionsService {
  /**
   * Retrieves all enrollments with pagination support.
   *
   * @param pageable pagination information (page number, size, sorting)
   * @return list of enrollment DTOs
   */
  List<EnrollmentDto> getAllInscriptions(Pageable pageable);
  /**
   * Searches for enrollments by student matching the given value.
   *
   * @param value search term to match against student information
   * @return list of matching enrollment DTOs
   */
  List<EnrollmentDto> findStudentByValue(String value);
  /**
   * Creates a new enrollment with associated documents.
   *
   * @param inscriptionDto enrollment data transfer object
   * @param grades file containing student grades
   * @param letter file containing enrollment letter
   * @return the created enrollment DTO
   */
  EnrollmentDto addInscription(EnrollmentDto inscriptionDto, MultipartFile grades,
      MultipartFile letter);
  /**
   * Updates the exam date for a specific enrollment.
   *
   * @param id unique identifier of the enrollment
   * @param enrollmentUpdate DTO containing the new exam date
   */
  void updateExamDate(String id, EnrollmentUpdateDto enrollmentUpdate);
  /**
   * Updates the process status for a specific enrollment.
   *
   * @param id unique identifier of the enrollment
   * @param enrollmentUpdate DTO containing the new process status
   */
  void updateProcessStatus(String id, EnrollmentUpdateDto enrollmentUpdate);
  /**
   * Updates WhatsApp communication permission for a specific enrollment.
   *
   * @param id unique identifier of the enrollment
   * @param enrollmentUpdate DTO containing the WhatsApp permission status
   */
  void updateWhatsappPermission(String id, EnrollmentUpdateDto enrollmentUpdate);
  /**
   * Updates general enrollment information.
   *
   * @param id unique identifier of the enrollment
   * @param enrollmentUpdate DTO containing updated enrollment data
   */
  void updateEnrollment(String id, EnrollmentUpdateDto enrollmentUpdate);
  /**
   * Deletes a specific document associated with an enrollment.
   *
   * @param documentId unique identifier of the document to delete
   */
  void deleteDocument(Long documentId);
  /**
   * Saves a new document associated with an enrollment.
   *
   * @param documentType type/category of the document
   * @param enrollmentId identifier of the associated enrollment
   * @param file the document file to be saved
   * @return DTO containing information about the saved document
   */
  DocumentDto saveDocument(String documentType, Long enrollmentId, MultipartFile file);
  /**
   * Retrieves pending enrollments for a specific student.
   *
   * @param idNumber student identification number
   * @return iterable collection of pending enrollment DTOs
   */
  Iterable<EnrollmentDto> findPendingEnrollmentsByStudentId(String idNumber);
}
