package cr.co.ctpcit.citsacbackend.data.entities.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import cr.co.ctpcit.citsacbackend.data.enums.Relationship;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class StudentEntityTest {
  @Autowired
  private JacksonTester<StudentEntity> json;

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

    StudentEntity student = new StudentEntity();
    student.setId(1L);
    student.setPerson(person);
    student.setBirthDate(LocalDate.parse("2007-12-03"));
    student.setPreviousSchool("Manhattan School");
    student.setHasAccommodations(false);
    student.setParents(new ArrayList<>());

    assertThat(json.write(student)).isStrictlyEqualToJson("StudentEntityJsonExpected.json");
    assertThat(json.write(student)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(student)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
    assertThat(json.write(student)).hasJsonPathStringValue("@.previousSchool");
    assertThat(json.write(student)).extractingJsonPathStringValue("@.previousSchool")
        .isEqualTo("Manhattan School");
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
          "birthDate": "2007-12-03",
          "previousSchool": "Manhattan School",
          "hasAccommodations": false,
          "parents": []
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

    assertThat(json.parse(expected)).isEqualTo(
        new StudentEntity(1L, person, LocalDate.parse("2007-12-03"), "Manhattan School", false,
            new ArrayList<>()));
    assertThat(json.parseObject(expected).getId()).isEqualTo(1);
    assertThat(json.parseObject(expected).getBirthDate()).isEqualTo("2007-12-03");
  }

  @Test
  void deserializeJson_with_parent() throws Exception {
    String expected = """
        {
          "id": 2,
          "person": {
            "id": 2,
            "firstName": "Mark",
            "firstSurname": "Doe",
            "secondSurname": "Johnson",
            "idType": "CC",
            "idNumber": "987654321",
            "fullSurname": "Doe Johnson"
          },
          "birthDate": "2007-12-03",
          "previousSchool": "Manhattan School",
          "hasAccommodations": false,
          "parents": [
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
          ]
        }
        """;

    PersonEntity person1 = new PersonEntity();
    person1.setId(1L);
    person1.setFirstName("John");
    person1.setFirstSurname("Doe");
    person1.setSecondSurname("Smith");
    person1.setIdType(IdType.CC);
    person1.setIdNumber("123456789");
    person1.setFullSurname("Doe Smith");

    ParentEntity parent = new ParentEntity();
    parent.setId(1L);
    parent.setPerson(person1);
    parent.setEmail("johndoe@mtmail.com");
    parent.setPhoneNumber("88889999");
    parent.setRelationship(Relationship.F);
    parent.setDaiExam(null);
    parent.setStudents(new ArrayList<>());

    PersonEntity person = new PersonEntity();
    person.setId(2L);
    person.setFirstName("Mark");
    person.setFirstSurname("Doe");
    person.setSecondSurname("Johnson");
    person.setIdType(IdType.CC);
    person.setIdNumber("987654321");
    person.setFullSurname("Doe Johnson");

    StudentEntity student = new StudentEntity();
    student.setId(2L);
    student.setPerson(person);
    student.setBirthDate(LocalDate.parse("2007-12-03"));
    student.setPreviousSchool("Manhattan School");
    student.setHasAccommodations(false);
    parent.addStudent(student);

    assertThat(json.parse(expected)).isEqualTo(student);
    assertThat(json.parseObject(expected).getId()).isEqualTo(2);
    assertThat(json.parseObject(expected).getBirthDate()).isEqualTo("2007-12-03");
  }
}
