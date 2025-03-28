package cr.co.ctpcit.citsacbackend.logic.services.inscriptions;

import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.DocumentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentUpdateDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface InscriptionsService {

  List<EnrollmentDto> getAllInscriptions(Pageable pageable);

  List<EnrollmentDto> findStudentByValue(String value);

  EnrollmentDto addInscription(EnrollmentDto inscriptionDto, MultipartFile grades,
      MultipartFile letter);

  void updateExamDate(String id, EnrollmentUpdateDto enrollmentUpdate);

  void updateProcessStatus(String id, EnrollmentUpdateDto enrollmentUpdate);

  void updateWhatsappPermission(String id, EnrollmentUpdateDto enrollmentUpdate);

  void updateEnrollment(String id, EnrollmentUpdateDto enrollmentUpdate);

  void deleteDocument(Long documentId);

  DocumentDto saveDocument(String documentType, Long enrollmentId, MultipartFile file);

  Iterable<EnrollmentDto> findPendingEnrollmentsByStudentId(String idNumber);
}
