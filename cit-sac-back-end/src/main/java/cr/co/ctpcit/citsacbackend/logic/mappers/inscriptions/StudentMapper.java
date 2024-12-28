package cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions;



import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.StudentDto;

import java.util.List;

/**
 * Mapper for {@link StudentEntity} and {@link StudentDto} This class is used to convert
 * {@link StudentEntity} to {@link StudentDto}
 */
public class StudentMapper {

  /*public static StudentDto convertToDto(StudentEntity studentEntity) {
    return StudentDto.builder().id(studentEntity.getId())
        .enrollments(EnrollmentMapper.convertToDtoList(studentEntity.getEnrollments()))
        .parents(ParentGuardianMapper.convertToDtoList(studentEntity.getParents()))
        .firstName(studentEntity.getFirstName()).firstSurname(studentEntity.getFirstSurname())
        .secondSurname(studentEntity.getSecondSurname()).birthDate(studentEntity.getBirthDate())
        .idType(studentEntity.getIdType()).idNumber(studentEntity.getIdNumber())
        .previousSchool(studentEntity.getPreviousSchool())
        .hasAccommodations(studentEntity.getHasAccommodations()).build();
  }*/

  /*public static List<StudentDto> convertToDtoList(List<StudentEntity> studentEntities) {
    if (studentEntities == null) {
      return null;
    }
    return studentEntities.stream().map(StudentMapper::convertToDto).toList();
  }*/

  public static StudentEntity convertToEntity(StudentDto inscription) {
    return StudentEntity.builder()
        .id(inscription.id())
        .birthDate(inscription.birthDate())
        .previousSchool(inscription.previousSchool())
        .hasAccommodations(inscription.hasAccommodations())
        .build();
  }
}
