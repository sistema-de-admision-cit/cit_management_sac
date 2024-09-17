package cr.co.ctpcit.citsacbackend.logic.mappers;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.AddressEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.AddressDto;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class AddressMapper {
    public static List<AddressDto> convertToDtoList(List<AddressEntity> addresses) {
        return addresses.stream().map(AddressMapper::convertToDto).toList();
    }

    public static AddressDto convertToDto(AddressEntity addressEntity) {
        return AddressDto.builder()
                .id(addressEntity.getId())
                .province(addressEntity.getProvince())
                .city(addressEntity.getCity())
                .district(addressEntity.getDistrict())
                .addressInfo(addressEntity.getAddressInfo())
                .build();
    }

    public static List<AddressEntity> convertToEntityList(List<AddressDto> addresses) {
        return addresses.stream().map(AddressMapper::convertToEntity).toList();
    }

    public static AddressEntity convertToEntity(AddressDto addressDto) {
        return AddressEntity.builder()
                .id(addressDto.id())
                .country(addressDto.country())
                .province(addressDto.province())
                .city(addressDto.city())
                .district(addressDto.district())
                .addressInfo(addressDto.addressInfo())
                .build();
    }
}
