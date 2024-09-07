package cr.co.ctpcit.citsacbackend.logic.mappers;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscription.ParentsGuardianEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscription.StudentEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.InscriptionDto;

import java.util.List;

public class InscriptionMapper {
    public static InscriptionDto convertToDto(StudentEntity student,
                                              List<EnrollmentEntity> studentEnrollments,
                                              List<ParentsGuardianEntity> studentParents) {

        return InscriptionDto.builder()
                .student(StudentMapper.convertToDto(student))
                .parents(ParentGuardianMapper.convertToDtoList(studentParents))
                .enrollments(EnrollmentMapper.convertToDtoList(studentEnrollments))
                .build();
    }
}
