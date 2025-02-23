package cr.co.ctpcit.citsacbackend.data.entities.inscriptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import cr.co.ctpcit.citsacbackend.data.enums.Relationship;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ParentEntityTest {
  @Autowired
  private JacksonTester<ParentEntity> json;

  @Test
  void serializeJson() throws Exception {
    ParentEntity parent = TestProvider.provideParent();
    parent.addAddress(TestProvider.provideAddress());

    assertThat(json.write(parent)).isStrictlyEqualToJson("ParentEntityJsonExpected.json");
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
          "parentPerson": {
            "id": 1,
            "firstName": "Carlos",
            "firstSurname": "Rodríguez",
            "secondSurname": "Morales",
            "idType": "CC",
            "idNumber": "900321654",
            "fullSurname": "Rodríguez Morales"
          },
          "phoneNumber": "876543210",
          "email": "carlos.rod@example.com",
          "relationship": "F",
          "daiExam": null,
          "addresses": [
            {
              "id": 1,
              "country": "Costa Rica",
              "province": "San José",
              "city": "San José",
              "district": "Carmen",
              "addressInfo": "Avenida Central 100"
            }
          ]
        }
        """;

    ParentEntity parent = TestProvider.provideParent();
    parent.addAddress(TestProvider.provideAddress());

    assertThat(json.parse(expected)).isEqualTo(parent);
    assertThat(json.parseObject(expected).getId()).isEqualTo(1);
    assertThat(json.parseObject(expected).getEmail()).isEqualTo("carlos.rod@example.com");
  }
}
