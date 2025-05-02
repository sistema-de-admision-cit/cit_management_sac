package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.TestProvider;
import cr.co.ctpcit.citsacbackend.data.enums.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class EnrollmentDtoTest {

  @Autowired
  private JacksonTester<EnrollmentDto> json;

  @Test
  void testSerialize() throws Exception {
    EnrollmentDto enrollment = TestProvider.provideEnrollmentDto();

    long timestamp = 856332114336L;
    String documentUrlPostfix =
        "grades_" + enrollment.student().person().idNumber() + "_" + timestamp + ".pdf";

    DocumentDto document =
        new DocumentDto(1L, documentUrlPostfix, DocType.OT, "Documento de Notas");
    enrollment.documents().add(document);

    assertThat(json.write(enrollment)).isStrictlyEqualToJson("EnrollmentDtoJsonExpected.json");
    assertThat(json.write(enrollment)).hasEmptyJsonPathValue("@.id");
    assertThat(json.write(enrollment)).extractingJsonPathNumberValue("@.id").isNull();
    assertThat(json.write(enrollment)).hasJsonPathValue("@.student");
    assertThat(json.write(enrollment)).extractingJsonPathStringValue("@.student.previousSchool")
        .isEqualTo("Escuela La Sabana");
    assertThat(json.write(enrollment)).hasJsonPathStringValue("@.status");
  }

  @Test
  void testDeserialize() throws Exception {
    String content = """
        {
          "id": null,
          "student": {
            "id": 11,
            "person": {
              "id": 11,
              "firstName": "Andrés",
              "firstSurname": "Rodríguez",
              "secondSurname": "Morales",
              "idType": "CC",
              "idNumber": "200123654"
            },
            "birthDate": "2010-03-12",
            "previousSchool": "Escuela La Sabana",
            "hasAccommodations": false,
            "previousGrades": 8.5,
            "parents": [
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
              "id": 1,
              "documentUrlPostfix": "grades_200123654_856332114336.pdf",
              "documentType": "OT",
              "documentName": "Documento de Notas"
            }
          ]
        }
        """;

    EnrollmentDto enrollment = TestProvider.provideEnrollmentDto();

    long timestamp = 856332114336L;
    String documentUrlPostfix =
        "grades_" + enrollment.student().person().idNumber() + "_" + timestamp + ".pdf";

    DocumentDto document =
        new DocumentDto(1L, documentUrlPostfix, DocType.OT, "Documento de Notas");
    enrollment.documents().add(document);

    assertThat(json.parse(content)).isEqualTo(enrollment);
    assertThat(json.parseObject(content).id()).isNull();
    assertThat(json.parseObject(content).student().previousSchool()).isEqualTo("Escuela La Sabana");
    assertThat(json.parseObject(content).status()).isEqualTo(ProcessStatus.PENDING);

  }
}
