package cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.ParentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.ParentDto;

import java.util.List;

/**
 * Mapper for {@link ParentDto} This class is used to convert from {@link ParentDto} to
 * {@link ParentEntity} and vice versa
 */
public class ParentMapper {
  /*public static ParentsGuardianDto convertToDto(ParentGuardianStudentEntity parentGuardianEntity) {
    return ParentsGuardianDto.builder().id(parentGuardianEntity.getParentGuardian().getId())
        .firstName(parentGuardianEntity.getParentGuardian().getFirstName())
        .firstSurname(parentGuardianEntity.getParentGuardian().getFirstSurname())
        .secondSurname(parentGuardianEntity.getParentGuardian().getSecondSurname())
        .idType(parentGuardianEntity.getParentGuardian().getIdType())
        .idNumber(parentGuardianEntity.getParentGuardian().getIdNumber())
        .phoneNumber(parentGuardianEntity.getParentGuardian().getPhoneNumber())
        .email(parentGuardianEntity.getParentGuardian().getEmail())
        .relationship(parentGuardianEntity.getParentGuardian().getRelationship()).addresses(
            AddressMapper.convertToDtoList(parentGuardianEntity.getParentGuardian().getAddresses()))
        .build();
  }*/

  /*public static List<ParentDto> convertToDtoList(
      List<ParentEntity> parentsGuardianEntities) {
    return parentsGuardianEntities.stream().map(ParentMapper::convertToDto).toList();
  }*/

  public static ParentEntity convertToEntity(ParentDto parentDto) {
    return ParentEntity.builder().id(parentDto.id()).phoneNumber(parentDto.phoneNumber())
        .email(parentDto.email()).relationship(parentDto.relationship()).build();
  }

  public static List<ParentEntity> convertToEntityList(List<ParentDto> parents) {
    return parents.stream().map(ParentMapper::convertToEntity).toList();
  }
}
