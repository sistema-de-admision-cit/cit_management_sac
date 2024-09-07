package cr.co.ctpcit.citsacbackend.logic.dto.inscription;

import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record InscriptionDto(StudentDto student,
                             List<ParentsGuardianDto> parents,
                             List<EnrollmentDto> enrollments) implements Serializable {
}