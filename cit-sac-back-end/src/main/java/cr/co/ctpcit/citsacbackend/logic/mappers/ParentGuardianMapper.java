package cr.co.ctpcit.citsacbackend.logic.mappers;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.ParentGuardianStudentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscription.ParentsGuardianEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.ParentsGuardianDto;

import java.util.List;

/**
 * Mapper for {@link ParentsGuardianDto} This class is used to convert from
 * {@link ParentsGuardianDto} to {@link ParentsGuardianEntity} and vice versa
 */
public class ParentGuardianMapper {
  public static ParentsGuardianDto convertToDto(ParentGuardianStudentEntity parentGuardianEntity) {
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
  }

  public static List<ParentsGuardianDto> convertToDtoList(
      List<ParentGuardianStudentEntity> parentsGuardianEntities) {
    return parentsGuardianEntities.stream().map(ParentGuardianMapper::convertToDto).toList();
  }

  public static ParentsGuardianEntity convertToEntity(ParentsGuardianDto parentDto) {
    return ParentsGuardianEntity.builder().id(parentDto.id()).firstName(parentDto.firstName())
        .firstSurname(parentDto.firstSurname()).secondSurname(parentDto.secondSurname())
        .idType(parentDto.idType()).idNumber(parentDto.idNumber())
        .phoneNumber(parentDto.phoneNumber()).email(parentDto.email())
        .relationship(parentDto.relationship()).build();
  }

  public static List<ParentsGuardianEntity> convertToEntityList(List<ParentsGuardianDto> parents) {
    return parents.stream().map(ParentGuardianMapper::convertToEntity).toList();
  }
}
