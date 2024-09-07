package cr.co.ctpcit.citsacbackend.logic.dto.inscription;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.ParentGuardianStudentEntity;

import java.io.Serializable;

/**
 * DTO for {@link ParentGuardianStudentEntity}
 */
public record ParentGuardianStudentDto(StudentDto student,
                                       ParentsGuardianDto parentGuardian) implements Serializable {
}