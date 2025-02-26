package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.TestProvider;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.AddressEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class AddressDtoTest {

  @Autowired
  private JacksonTester<AddressDto> json;

  @Test
  void serializeJson() throws Exception {
    AddressDto address = TestProvider.provideAddressDto();

    assertThat(json.write(address)).isStrictlyEqualToJson("AddressDtoJsonExpected.json");
    assertThat(json.write(address)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(address)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
    assertThat(json.write(address)).hasJsonPathStringValue("@.country");
    assertThat(json.write(address)).extractingJsonPathStringValue("@.country")
        .isEqualTo("Costa Rica");
  }

  @Test
  void deserializeJson() throws Exception {
    String content = """
        {
          "id": 1,
          "country": "Costa Rica",
          "province": "San José",
          "city": "San José",
          "district": "Carmen",
          "addressInfo": "Avenida Central 100"
        }
        """;
    AddressDto address = TestProvider.provideAddressDto();

    assertThat(json.parse(content)).isEqualTo(address);
    assertThat(json.parseObject(content).id()).isEqualTo(1);
    assertThat(json.parseObject(content).country()).isEqualTo("Costa Rica");
  }
}
