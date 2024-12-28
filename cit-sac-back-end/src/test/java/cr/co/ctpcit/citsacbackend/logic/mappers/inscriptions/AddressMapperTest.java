package cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.AddressEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.AddressDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressMapperTest {
  @Test
  void testConvertToDto() {
    AddressEntity addressEntity = new AddressEntity();
    addressEntity.setId(1L);
    addressEntity.setCountry("Costa Rica");
    addressEntity.setProvince("San José");
    addressEntity.setCity("San José");
    addressEntity.setDistrict("Pavas");
    addressEntity.setAddressInfo("100 meters south of the church");

    AddressDto addressDto = AddressMapper.convertToDto(addressEntity);

    assertNotNull(addressDto);
    assertEquals(addressEntity.getId(), addressDto.id());
    assertEquals(addressEntity.getCountry(), addressDto.country());
    assertEquals(addressEntity.getProvince(), addressDto.province());
    assertEquals(addressEntity.getCity(), addressDto.city());
    assertEquals(addressEntity.getDistrict(), addressDto.district());
    assertEquals(addressEntity.getAddressInfo(), addressDto.addressInfo());
  }

  @Test
  void testConvertToEntity() {
    AddressDto addressDto =
        AddressDto.builder().id(1L).country("Costa Rica").province("San José").city("San José")
            .district("Pavas").addressInfo("100 meters south of the church").build();

    AddressEntity addressEntity = AddressMapper.convertToEntity(addressDto);

    assertNotNull(addressEntity);
    assertEquals(addressDto.id(), addressEntity.getId());
    assertEquals(addressDto.country(), addressEntity.getCountry());
    assertEquals(addressDto.province(), addressEntity.getProvince());
    assertEquals(addressDto.city(), addressEntity.getCity());
    assertEquals(addressDto.district(), addressEntity.getDistrict());
    assertEquals(addressDto.addressInfo(), addressEntity.getAddressInfo());
  }
}
