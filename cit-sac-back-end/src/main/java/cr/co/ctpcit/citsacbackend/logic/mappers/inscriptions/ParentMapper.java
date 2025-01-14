package cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.ParentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.ParentsStudentsEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.ParentDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper for {@link ParentDto} This class is used to convert from {@link ParentDto} to
 * {@link ParentEntity} and vice versa
 */
public class ParentMapper {
  public static ParentDto convertToDto(ParentEntity parentEntity) {
    return ParentDto.builder().id(parentEntity.getId())
        .person(PersonMapper.convertToDto(parentEntity.getPerson()))
        .phoneNumber(parentEntity.getPhoneNumber()).email(parentEntity.getEmail())
        .relationship(parentEntity.getRelationship())
        .addresses(AddressMapper.convertToDtoList(parentEntity.getAddresses())).build();
  }

  public static List<ParentDto> convertToDtoList(List<ParentsStudentsEntity> parentEntities) {
    return parentEntities.stream().map(p -> ParentMapper.convertToDto(p.getParent())).toList();
  }

  public static ParentEntity convertToEntity(ParentDto parentDto) {
    return ParentEntity.builder().id(parentDto.id()).phoneNumber(parentDto.phoneNumber())
        .email(parentDto.email()).relationship(parentDto.relationship())
        .build();
  }

  public static List<ParentEntity> convertToEntityList(List<ParentDto> parents) {
    return parents.stream().map(ParentMapper::convertToEntity).toList();
  }
}
