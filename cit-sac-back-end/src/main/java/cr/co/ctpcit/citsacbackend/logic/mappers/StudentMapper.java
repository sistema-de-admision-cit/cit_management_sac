package cr.co.ctpcit.citsacbackend.logic.mappers;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.StudentEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;

import java.util.List;

public class StudentMapper {
    public static StudentDto convertToDto(StudentEntity studentEntity) {
        return StudentDto.builder()
                .enrollments(EnrollmentMapper.convertToDtoList(studentEntity.getEnrollments()))
                .parents(ParentGuardianMapper.convertToDtoList(studentEntity.getParents()))
                .firstName(studentEntity.getFirstName())
                .firstSurname(studentEntity.getFirstSurname())
                .secondSurname(studentEntity.getSecondSurname())
                .birthDate(studentEntity.getBirthDate())
                .idType(studentEntity.getIdType())
                .idNumber(studentEntity.getIdNumber())
                .previousSchool(studentEntity.getPreviousSchool())
                .hasAccommodations(studentEntity.getHasAccommodations())
                .build();
    }

    public static List<StudentDto> convertToDtoList(List<StudentEntity> studentEntities) {
        return studentEntities.stream().map(StudentMapper::convertToDto).toList();
    }
}
