package cr.co.ctpcit.citsacbackend.data.entities.inscriptions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class AddressEntityTest {
  @Autowired
  private JacksonTester<AddressEntity> json;

  @Test
  void serializeJson() throws Exception {
    AddressEntity address = new AddressEntity();
    address.setId(1L);
    address.setCountry("Costa Rica");
    address.setProvince("San José");
    address.setCity("San José");
    address.setDistrict("Carmen");
    address.setAddressInfo("Avenida Central 100");

    assertThat(json.write(address)).isStrictlyEqualToJson("AddressEntityJsonExpected.json");
    assertThat(json.write(address)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(address)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
    assertThat(json.write(address)).hasJsonPathStringValue("@.country");
    assertThat(json.write(address)).extractingJsonPathStringValue("@.country")
        .isEqualTo("Costa Rica");
  }

  @Test
  void deserializeJson() throws Exception {
    String expected = """
        {
          "id": 1,
          "country": "Costa Rica",
          "province": "San José",
          "city": "San José",
          "district": "Carmen",
          "addressInfo": "Avenida Central 100"
        }
        """;
    AddressEntity address = new AddressEntity();
    address.setId(1L);
    address.setCountry("Costa Rica");
    address.setProvince("San José");
    address.setCity("San José");
    address.setDistrict("Carmen");
    address.setAddressInfo("Avenida Central 100");

    assertThat(json.parse(expected)).isEqualTo(address);
    assertThat(json.parseObject(expected).getId()).isEqualTo(1);
    assertThat(json.parseObject(expected).getCountry()).isEqualTo("Costa Rica");
  }
}
