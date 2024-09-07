package cr.co.ctpcit.citsacbackend.logic.dto.inscription;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record InscriptionDto(StudentDto student,
                             ParentsGuardianDto parentGuardian,
                             EnrollmentDto enrollment) implements Serializable {
}