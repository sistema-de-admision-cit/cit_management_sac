package cr.co.ctpcit.citsacbackend.data.entities.inscriptions;

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
    PersonEntity person = new PersonEntity();
    person.setId(1L);
    person.setFirstName("John");
    person.setFirstSurname("Doe");
    person.setSecondSurname("Smith");
    person.setIdType(IdType.CC);
    person.setIdNumber("123456789");
    person.setFullSurname("Doe Smith");

    ParentEntity parent = new ParentEntity();
    parent.setId(1L);
    parent.setPerson(person);
    parent.setEmail("johndoe@mtmail.com");
    parent.setPhoneNumber("88889999");
    parent.setRelationship(Relationship.F);
    parent.setDaiExam(null);
    parent.setStudents(new ArrayList<>());

    assertThat(json.write(parent)).isStrictlyEqualToJson("ParentEntityJsonExpected.json");
    assertThat(json.write(parent)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(parent)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
    assertThat(json.write(parent)).hasJsonPathStringValue("@.email");
    assertThat(json.write(parent)).extractingJsonPathStringValue("@.email")
        .isEqualTo("johndoe@mtmail.com");
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
          "daiExam": null
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

    ParentEntity parent = new ParentEntity();
    parent.setId(1L);
    parent.setPerson(person);
    parent.setEmail("johndoe@mtmail.com");
    parent.setPhoneNumber("88889999");
    parent.setRelationship(Relationship.F);
    parent.setDaiExam(null);
    parent.setStudents(new ArrayList<>());

    assertThat(json.parse(expected)).isEqualTo(parent);
    assertThat(json.parseObject(expected).getId()).isEqualTo(1);
    assertThat(json.parseObject(expected).getEmail()).isEqualTo("johndoe@mtmail.com");
  }
}
