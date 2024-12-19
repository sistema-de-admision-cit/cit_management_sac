package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import cr.co.ctpcit.citsacbackend.data.enums.Relationship;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ParentDtoTest {

  @Autowired
  private JacksonTester<ParentDto> json;

  @Test
  void serializeJson() throws Exception {
    PersonDto person = new PersonDto(1L, "John", "Doe", "Smith", IdType.CC, "123456789");
    ParentDto parent = new ParentDto(1L, person, "88889999", "johndoe@mtmail.com", Relationship.F,
        new ArrayList<>());

    assertThat(json.write(parent)).isStrictlyEqualToJson("ParentDtoJsonExpected.json");
    assertThat(json.write(parent)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(parent)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
    assertThat(json.write(parent)).hasJsonPathStringValue("@.email");
    assertThat(json.write(parent)).extractingJsonPathStringValue("@.email").isEqualTo("johndoe@mtmail.com");
  }

  @Test
  void deserializeJson() throws Exception {
    String expected = """
        {
          "id": 1,
          "person": {
            "id": 1,
            "firstName": "John",
            "firstSurname": "Doe",
            "secondSurname": "Smith",
            "idType": "CC",
            "idNumber": "123456789",
            "fullSurname": "Doe Smith"
          },
          "phoneNumber": "88889999",
          "email": "johndoe@mtmail.com",
          "relationship": "F",
          "addresses": []
        }
        """;

    PersonDto person = new PersonDto(1L, "John", "Doe", "Smith", IdType.CC, "123456789");
    ParentDto parent = new ParentDto(1L, person, "88889999", "johndoe@mtmail.com", Relationship.F,
        new ArrayList<>());

    assertThat(json.parse(expected)).isEqualTo(parent);
    assertThat(json.parseObject(expected).id()).isEqualTo(1);
    assertThat(json.parseObject(expected).email()).isEqualTo("johndoe@mtmail.com");
  }
}
