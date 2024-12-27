package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class EnrollmentDtoTest {

  @Autowired
  private JacksonTester<EnrollmentDto> json;

  private StudentDto student;

  @BeforeEach
  void setUp() {
    PersonDto parentPerson = new PersonDto(1L, "John", "Doe", "Smith", IdType.CC, "123456789");
    ParentDto parent =
        new ParentDto(1L, parentPerson, "88889999", "johndoe@mtmail.com", Relationship.F,
            new ArrayList<>());
    AddressDto address = new AddressDto(1L, "Costa Rica", "San José", "San José", "Pavas",
        "Ruta 104, Iglesia Ma. Reina del Universo, calle principal., Pavas, Costa Rica");
    parent.addresses().add(address);

    PersonDto studentPerson = new PersonDto(2L, "Mark", "Doe", "Johnson", IdType.CC, "987654321");
    student =
        new StudentDto(2L, studentPerson, LocalDate.parse("2007-12-03"), "Manhattan School", false,
            new ArrayList<>());
    student.parents().add(parent);
  }

  @Test
  void testSerialize() throws Exception {
    EnrollmentDto enrollment =
        new EnrollmentDto(null, student, ProcessStatus.PENDING, Grades.FIRST, KnownThrough.OT,
            LocalDate.parse("2024-12-15"), true, false, new ArrayList<>());
    DocumentDto document = new DocumentDto(new DocumentIdDto(1L, 1L), "Document 1", DocType.OT);
    enrollment.documents().add(document);

    assertThat(json.write(enrollment)).isStrictlyEqualToJson("EnrollmentDtoJsonExpected.json");
    assertThat(json.write(enrollment)).hasEmptyJsonPathValue("@.id");
    assertThat(json.write(enrollment)).extractingJsonPathNumberValue("@.id").isNull();
    assertThat(json.write(enrollment)).hasJsonPathValue("@.student");
    assertThat(json.write(enrollment)).extractingJsonPathStringValue("@.student.previousSchool")
        .isEqualTo("Manhattan School");
    assertThat(json.write(enrollment)).hasJsonPathStringValue("@.status");
  }

  @Test
  void testDeserialize() throws Exception {
    String content = """
        {
          "id": null,
          "student": {
            "id": 2,
            "person": {
              "id": 2,
              "firstName": "Mark",
              "firstSurname": "Doe",
              "secondSurname": "Johnson",
              "idType": "CC",
              "idNumber": "987654321"
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
                  "idNumber": "123456789"
                },
                "phoneNumber": "88889999",
                "email": "johndoe@mtmail.com",
                "relationship": "F",
                "addresses": [
                  {
                    "id": 1,
                    "country": "Costa Rica",
                    "province": "San José",
                    "city": "San José",
                    "district": "Pavas",
                    "addressInfo": "Ruta 104, Iglesia Ma. Reina del Universo, calle principal., Pavas, Costa Rica"
                  }
                ]
              }
            ]
          },
          "status": "PENDING",
          "gradeToEnroll": "FIRST",
          "knownThrough": "OT",
          "examDate": "2024-12-15",
          "consentGiven": true,
          "whatsappNotification": false,
          "documents": [
            {
              "id": {
                "documentId": 1,
                "enrollmentId": 1
              },
              "documentName": "Document 1",
              "documentType": "OT"
            }
          ]
        }
        """;

    EnrollmentDto enrollment =
        new EnrollmentDto(null, student, ProcessStatus.PENDING, Grades.FIRST, KnownThrough.OT,
            LocalDate.parse("2024-12-15"), true, false, new ArrayList<>());
    DocumentDto document = new DocumentDto(new DocumentIdDto(1L, 1L), "Document 1", DocType.OT);
    enrollment.documents().add(document);

    assertThat(json.parse(content)).isEqualTo(enrollment);
    assertThat(json.parseObject(content).id()).isNull();
    assertThat(json.parseObject(content).student().previousSchool()).isEqualTo("Manhattan School");
    assertThat(json.parseObject(content).status()).isEqualTo(ProcessStatus.PENDING);

  }
}
