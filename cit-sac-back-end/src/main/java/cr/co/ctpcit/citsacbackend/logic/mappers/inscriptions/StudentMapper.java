package cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.StudentDto;


/**
 * Mapper for {@link StudentEntity} and {@link StudentDto} This class is used to convert
 * {@link StudentEntity} to {@link StudentDto}
 */
public class StudentMapper {

  public static StudentDto convertToDto(StudentEntity studentEntity) {
    return StudentDto.builder()
        .id(studentEntity.getId())
        .person(PersonMapper.convertToDto(studentEntity.getPerson()))
        .parents(ParentMapper.convertToDtoList(studentEntity.getParents()))
        .previousSchool(studentEntity.getPreviousSchool())
        .birthDate(studentEntity.getBirthDate())
        .hasAccommodations(studentEntity.getHasAccommodations()).build();
  }

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
