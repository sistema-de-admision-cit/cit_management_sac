package cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.AddressEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.AddressDto;

import java.util.List;

/**
 * Mapper class for the {@link AddressEntity} and {@link AddressDto} classes. This class is used to
 * convert an {@link AddressEntity} to an {@link AddressDto} and vice versa.
 */
public class AddressMapper {

  /**
   * Converts a list of {@link AddressEntity} objects to a list of {@link AddressDto} objects.
   *
   * @param addresses the list of {@link AddressEntity} objects to be converted
   * @return a list of {@link AddressDto} objects representing the converted data
   */
  public static List<AddressDto> convertToDtoList(List<AddressEntity> addresses) {
    return addresses.stream().map(AddressMapper::convertToDto).toList();
  }

  /**
   * Converts a {@link AddressEntity} to an {@link AddressDto}.
   *
   * @param addressEntity the {@link AddressEntity} to be converted
   * @return an {@link AddressDto} representing the converted data
   */
  public static AddressDto convertToDto(AddressEntity addressEntity) {
    return AddressDto.builder().id(addressEntity.getId()).country(addressEntity.getCountry())
        .province(addressEntity.getProvince()).city(addressEntity.getCity())
        .district(addressEntity.getDistrict()).addressInfo(addressEntity.getAddressInfo()).build();
  }

  public static AddressEntity convertToEntity(AddressDto addressDto) {
    return AddressEntity.builder().id(addressDto.id()).country(addressDto.country())
        .province(addressDto.province()).city(addressDto.city()).district(addressDto.district())
        .addressInfo(addressDto.addressInfo()).build();
  }

  /**
   * Converts a list of {@link AddressDto} objects to a list of {@link AddressEntity} objects.
   *
   * @param addresses the list of {@link AddressDto} objects to be converted
   * @return a list of {@link AddressEntity} objects representing the converted data
   */
  public static List<AddressEntity> convertToEntityList(List<AddressDto> addresses) {
    return addresses.stream().map(AddressMapper::convertToEntity).toList();
  }
}
