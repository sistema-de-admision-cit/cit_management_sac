package cr.co.ctpcit.citsacbackend.data.entities.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class EnrollmentEntityTest {
  @Autowired
  private JacksonTester<EnrollmentEntity> json;

  @Test
  void serializeJson() throws IOException {
    StudentEntity student = TestProvider.provideStudent();
    ParentEntity parent = TestProvider.provideParent();
    parent.addStudent(student);
    parent.addAddress(TestProvider.provideAddress());

    EnrollmentEntity enrollment = TestProvider.provideEnrollment();
    enrollment.setStudent(student);

    assertThat(json.write(enrollment)).isStrictlyEqualToJson("EnrollmentEntityJsonExpected.json");
    assertThat(json.write(enrollment)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(enrollment)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
    assertThat(json.write(enrollment)).hasJsonPathStringValue("@.status");
    assertThat(json.write(enrollment)).extractingJsonPathStringValue("@.status")
        .isEqualTo("PENDING");
  }

  @Test
  void deserializeJson() throws IOException {
    String expected = """
        {
          "id": 1,
          "student": {
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
          },
          "status": "PENDING",
          "enrollmentDate": "2024-12-15T10:15:30Z",
          "gradeToEnroll": "FIRST",
          "knownThrough": "OT",
          "examDate": "2024-12-15",
          "consentGiven": true,
          "whatsappNotification": false,
          "documents": []
        }
        """;

    StudentEntity student = TestProvider.provideStudent();
    ParentEntity parent = TestProvider.provideParent();
    parent.addStudent(student);
    parent.addAddress(TestProvider.provideAddress());

    EnrollmentEntity enrollment = TestProvider.provideEnrollment();
    enrollment.setStudent(student);

    assertThat(json.parse(expected)).isEqualTo(enrollment);
    assertThat(json.parseObject(expected).getId()).isEqualTo(1);
    assertThat(json.parseObject(expected).getEnrollmentDate()).isEqualTo("2024-12-15T10:15:30Z");
  }
}
