package cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.ParentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.ParentsStudentsEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.ParentDto;

import java.util.List;

/**
 * Mapper for {@link ParentDto} This class is used to convert from {@link ParentDto} to
 * {@link ParentEntity} and vice versa
 */
public class ParentMapper {

  /**
   * Converts a {@link ParentEntity} to a {@link ParentDto}.
   *
   * @param parentEntity the {@link ParentEntity} to be converted
   * @return a {@link ParentDto} representing the converted data
   */
  public static ParentDto convertToDto(ParentEntity parentEntity) {
    return ParentDto.builder().id(parentEntity.getId())
        .person(PersonMapper.convertToDto(parentEntity.getParentPerson()))
        .phoneNumber(parentEntity.getPhoneNumber()).email(parentEntity.getEmail())
        .relationship(parentEntity.getRelationship())
        .addresses(AddressMapper.convertToDtoList(parentEntity.getAddresses())).build();
  }

  /**
   * Converts a list of {@link ParentsStudentsEntity} objects to a list of {@link ParentDto} objects.
   *
   * @param parentEntities the list of {@link ParentsStudentsEntity} objects to be converted
   * @return a list of {@link ParentDto} objects representing the converted data
   */
  public static List<ParentDto> convertToDtoList(List<ParentsStudentsEntity> parentEntities) {
    return parentEntities.stream().map(p -> ParentMapper.convertToDto(p.getParent())).toList();
  }

  /**
   * Converts a {@link ParentDto} to a {@link ParentEntity}.
   *
   * @param parentDto the {@link ParentDto} to be converted
   * @return a {@link ParentEntity} representing the converted data
   */
  public static ParentEntity convertToEntity(ParentDto parentDto) {
    return ParentEntity.builder().id(parentDto.id()).phoneNumber(parentDto.phoneNumber())
        .email(parentDto.email()).relationship(parentDto.relationship())
        .build();
  }

  /**
   * Converts a list of {@link ParentDto} objects to a list of {@link ParentEntity} objects.
   *
   * @param parents the list of {@link ParentDto} objects to be converted
   * @return a list of {@link ParentEntity} objects representing the converted data
   */
  public static List<ParentEntity> convertToEntityList(List<ParentDto> parents) {
    return parents.stream().map(ParentMapper::convertToEntity).toList();
  }
}
