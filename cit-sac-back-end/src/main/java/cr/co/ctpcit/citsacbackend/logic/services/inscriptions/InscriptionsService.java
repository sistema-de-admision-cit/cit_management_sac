package cr.co.ctpcit.citsacbackend.logic.services.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.DocumentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.EnrollmentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface InscriptionsService {
  List<StudentDto> getAllInscriptions(Pageable pageable);

  StudentDto findStudentById(@NotNull Long id);

  List<StudentDto> findStudentByValue(String value);

  EnrollmentDto addInscription(StudentDto inscriptionDto, MultipartFile grades,
      MultipartFile letter);

  StudentDto updateExamDate(String id, String date);

  boolean changeStatus(Long id, @NotNull ProcessStatus status);

  boolean changeWhatsappPermission(Long id, Boolean permission);

  Boolean updateEnrollment(Long enrollmentId, ProcessStatus status, String examDate,
      String whatsappPermission, String comment, Integer changedBy);

  boolean deleteDocument(Long documentId);

  DocumentDto saveDocument(String documentName, String documentType, Long enrollmentId);
}
