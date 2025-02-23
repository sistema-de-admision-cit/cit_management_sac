package cr.co.ctpcit.citsacbackend.data.entities.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import cr.co.ctpcit.citsacbackend.data.enums.Relationship;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class StudentEntityTest {
  @Autowired
  private JacksonTester<StudentEntity> json;

  @Test
  void serializeJson() throws Exception {
    StudentEntity student = TestProvider.provideStudent();
    ParentEntity parent = TestProvider.provideParent();
    parent.addStudent(student);
    parent.addAddress(TestProvider.provideAddress());

    assertThat(json.write(student)).isStrictlyEqualToJson("StudentEntityJsonExpected.json");
    assertThat(json.write(student)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(student)).extractingJsonPathNumberValue("@.id").isEqualTo(11);
    assertThat(json.write(student)).hasJsonPathStringValue("@.previousSchool");
    assertThat(json.write(student)).extractingJsonPathStringValue("@.previousSchool")
        .isEqualTo("Escuela La Sabana");
  }

  @Test
  void deserializeJson() throws Exception {
    String expected = """
        {
          "id": 11,
          "studentPerson": {
            "id": 11,
            "firstName": "Andrés",
            "firstSurname": "Rodríguez",
            "secondSurname": "Morales",
            "idType": "CC",
            "idNumber": "200123654",
            "fullSurname": "Rodríguez Morales"
          },
          "birthDate": "2010-03-12",
          "previousSchool": "Escuela La Sabana",
          "hasAccommodations": false,
          "parents": [
            {
              "parent": {
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
            }
          ]
        }
        """;

    PersonEntity person = new PersonEntity();
    person.setId(11L);
    person.setFirstName("Andrés");
    person.setFirstSurname("Rodríguez");
    person.setSecondSurname("Morales");
    person.setIdType(IdType.CC);
    person.setIdNumber("200123654");
    person.setFullSurname("Rodríguez Morales");

    StudentEntity student = new StudentEntity();
    student.setId(11L);
    student.setStudentPerson(person);
    student.setBirthDate(LocalDate.parse("2010-03-12"));
    student.setPreviousSchool("Escuela La Sabana");
    student.setHasAccommodations(false);

    PersonEntity person1 = new PersonEntity();
    person1.setId(1L);
    person1.setFirstName("Carlos");
    person1.setFirstSurname("Rodríguez");
    person1.setSecondSurname("Morales");
    person1.setIdType(IdType.CC);
    person1.setIdNumber("900321654");
    person1.setFullSurname("Rodríguez Morales");

    ParentEntity parent = new ParentEntity();
    parent.setId(1L);
    parent.setParentPerson(person1);
    parent.setEmail("carlos.rod@example.com");
    parent.setPhoneNumber("876543210");
    parent.setRelationship(Relationship.F);
    parent.setDaiExam(null);

    parent.addStudent(student);

    AddressEntity address = new AddressEntity();
    address.setId(1L);
    address.setCountry("Costa Rica");
    address.setProvince("San José");
    address.setCity("San José");
    address.setDistrict("Carmen");
    address.setAddressInfo("Avenida Central 100");

    parent.addAddress(address);

    assertThat(json.parse(expected)).isEqualTo(student);
    assertThat(json.parseObject(expected).getId()).isEqualTo(11);
    assertThat(json.parseObject(expected).getBirthDate()).isEqualTo("2010-03-12");
  }
}
