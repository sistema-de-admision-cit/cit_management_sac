package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.TestProvider;
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
    PersonDto person = TestProvider.provideParentPersonDto();

    assertThat(json.write(person)).isStrictlyEqualToJson("PersonDtoJsonExpected.json");
    assertThat(json.write(person)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(person)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
    assertThat(json.write(person)).hasJsonPathStringValue("@.firstName");
    assertThat(json.write(person)).extractingJsonPathStringValue("@.firstName").isEqualTo("Carlos");
  }

  @Test
  void deserializeJson() throws IOException {
    String content = """
        {
          "id": 1,
          "firstName": "Carlos",
          "firstSurname": "Rodríguez",
          "secondSurname": "Morales",
          "idType": "CC",
          "idNumber": "900321654"
        }
        """;
    PersonDto person = TestProvider.provideParentPersonDto();

    assertThat(json.parse(content)).isEqualTo(person);
    assertThat(json.parseObject(content).id()).isEqualTo(1);
    assertThat(json.parseObject(content).idNumber()).isEqualTo("900321654");
  }
}
