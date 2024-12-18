package cr.co.ctpcit.citsacbackend.data.entities.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.IdType;
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
    PersonEntity person = new PersonEntity();
    person.setId(1L);
    person.setFirstName("John");
    person.setFirstSurname("Doe");
    person.setSecondSurname("Smith");
    person.setIdType(IdType.CC);
    person.setIdNumber("123456789");
    person.setFullSurname("Doe Smith");

    assertThat(json.write(person)).isStrictlyEqualToJson("PersonEntityJsonExpected.json");
    assertThat(json.write(person)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(person)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
    assertThat(json.write(person)).hasJsonPathStringValue("@.firstName");
    assertThat(json.write(person)).extractingJsonPathStringValue("@.firstName").isEqualTo("John");
  }

  @Test
  void testDeserialize() throws Exception {
    String expected = """
        {
          "id": 1,
          "firstName": "John",
          "firstSurname": "Doe",
          "secondSurname": "Smith",
          "idType": "CC",
          "idNumber": "123456789",
          "fullSurname": "Doe Smith"
        }
        """;

    PersonEntity person = new PersonEntity();
    person.setId(1L);
    person.setFirstName("John");
    person.setFirstSurname("Doe");
    person.setSecondSurname("Smith");
    person.setIdType(IdType.CC);
    person.setIdNumber("123456789");
    person.setFullSurname("Doe Smith");

    assertThat(json.parse(expected)).isEqualTo(person);
    assertThat(json.parseObject(expected).getId()).isEqualTo(1);
    assertThat(json.parseObject(expected).getIdNumber()).isEqualTo("123456789");
  }
}
