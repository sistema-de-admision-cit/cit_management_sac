package cr.co.ctpcit.citsacbackend.logic.services.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InscriptionsService {
    List<StudentDto> getAllInscriptions(Pageable pageable);

    StudentDto findStudentById(@NotNull Long id);

    List<StudentDto> findStudentByValue(String value);

    StudentDto addInscription(StudentDto inscriptionDto);

    StudentDto updateExamDate(String id, String date);

    boolean changeStatus(Long id, @NotNull ProcessStatus status);

    boolean changeWhatsappPermission(Long id, Boolean permission);
}
