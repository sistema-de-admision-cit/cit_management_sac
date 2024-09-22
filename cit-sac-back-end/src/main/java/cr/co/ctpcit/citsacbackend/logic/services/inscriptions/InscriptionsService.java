package cr.co.ctpcit.citsacbackend.logic.services.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InscriptionsService {
  List<StudentDto> getAllInscriptions(Pageable pageable);

  StudentDto findStudentById(@NotNull Long id);

  List<StudentDto> findStudentByValue(String value);

  StudentDto addInscription(StudentDto inscriptionDto);

  StudentDto updateExamDate(String id, String date);

  boolean changeStatus(Long id, @NotNull ProcessStatus status);

  boolean changeWhatsappPermission(Long id, Boolean permission);

  Boolean updateEnrollment(Long enrollmentId, ProcessStatus status, String examDate,
      String whatsappPermission, String comment, Integer changedBy);

  boolean deleteDocument(Long documentId);
}
