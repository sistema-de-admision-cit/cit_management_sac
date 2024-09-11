package cr.co.ctpcit.citsacbackend.logic.services;

import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InscriptionsService {
    List<StudentDto> getAllInscriptions(Pageable pageable);
    StudentDto findStudentByIdNumber(@NotNull @Size(min = 9, max = 20) String id);
    StudentDto addInscription(StudentDto inscriptionDto);
}
