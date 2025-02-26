package cr.co.ctpcit.citsacbackend.data.entities.inscriptions;

import cr.co.ctpcit.citsacbackend.TestProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class PersonEntityTest {

  @Autowired
  private JacksonTester<PersonEntity> json;

  @Test
  void testSerialize() throws Exception {
    PersonEntity person = TestProvider.provideParentPerson();

    assertThat(json.write(person)).isStrictlyEqualToJson("PersonEntityJsonExpected.json");
    assertThat(json.write(person)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(person)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
    assertThat(json.write(person)).hasJsonPathStringValue("@.firstName");
    assertThat(json.write(person)).extractingJsonPathStringValue("@.firstName").isEqualTo("Carlos");
  }

  @Test
  void testDeserialize() throws Exception {
    String expected = """
        {
          "id": 1,
          "firstName": "Carlos",
          "firstSurname": "Rodríguez",
          "secondSurname": "Morales",
          "idType": "CC",
          "idNumber": "900321654",
          "fullSurname": "Rodríguez Morales"
        }
        """;

    PersonEntity person = TestProvider.provideParentPerson();

    assertThat(json.parse(expected)).isEqualTo(person);
    assertThat(json.parseObject(expected).getId()).isEqualTo(1);
    assertThat(json.parseObject(expected).getIdNumber()).isEqualTo("900321654");
  }
}
