package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class PersonDtoTest {

  @Autowired
  private JacksonTester<PersonDto> json;

  @Test
  void serializeJson() throws IOException {
    PersonDto person = new PersonDto(1L, "John", "Doe", "Smith", IdType.CC, "123456789");

    assertThat(json.write(person)).isStrictlyEqualToJson("PersonDtoJsonExpected.json");
    assertThat(json.write(person)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(person)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
    assertThat(json.write(person)).hasJsonPathStringValue("@.firstName");
    assertThat(json.write(person)).extractingJsonPathStringValue("@.firstName").isEqualTo("John");
  }

  @Test
  void deserializeJson() throws IOException {
    String content = """
        {
          "id": 1,
          "firstName": "John",
          "firstSurname": "Doe",
          "secondSurname": "Smith",
          "idType": "CC",
          "idNumber": "123456789"
        }
        """;
    PersonDto person = new PersonDto(1L, "John", "Doe", "Smith", IdType.CC, "123456789");

    assertThat(json.parse(content)).isEqualTo(person);
    assertThat(json.parseObject(content).id()).isEqualTo(1);
    assertThat(json.parseObject(content).idNumber()).isEqualTo("123456789");
  }
}
