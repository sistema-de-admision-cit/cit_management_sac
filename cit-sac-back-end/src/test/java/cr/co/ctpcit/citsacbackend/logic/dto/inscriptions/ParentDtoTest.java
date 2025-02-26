package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.TestProvider;
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
    ParentDto parent = TestProvider.provideParentDto();

    assertThat(json.write(parent)).isStrictlyEqualToJson("ParentDtoJsonExpected.json");
    assertThat(json.write(parent)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(parent)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
    assertThat(json.write(parent)).hasJsonPathStringValue("@.email");
    assertThat(json.write(parent)).extractingJsonPathStringValue("@.email")
        .isEqualTo("carlos.rod@example.com");
  }

  @Test
  void deserializeJson() throws Exception {
    String expected = """
        {
          "id": 1,
          "person": {
            "id": 1,
            "firstName": "Carlos",
            "firstSurname": "Rodríguez",
            "secondSurname": "Morales",
            "idType": "CC",
            "idNumber": "900321654"
          },
          "phoneNumber": "876543210",
          "email": "carlos.rod@example.com",
          "relationship": "F",
          "addresses": []
        }
        """;

    ParentDto parent = TestProvider.provideParentDto();

    assertThat(json.parse(expected)).isEqualTo(parent);
    assertThat(json.parseObject(expected).id()).isEqualTo(1);
    assertThat(json.parseObject(expected).email()).isEqualTo("carlos.rod@example.com");
  }
}
