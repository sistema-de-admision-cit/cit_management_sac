package cr.co.ctpcit.citsacbackend.data.entities.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import cr.co.ctpcit.citsacbackend.data.enums.KnownThrough;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
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

    EnrollmentEntity enrollment = new EnrollmentEntity();
    enrollment.setId(1L);
    enrollment.setStudent(student);
    enrollment.setStatus(ProcessStatus.PENDING);
    enrollment.setEnrollmentDate(Instant.parse("2024-12-15T10:15:30Z"));
    enrollment.setGradeToEnroll(Grades.FIRST);
    enrollment.setKnownThrough(KnownThrough.OT);
    enrollment.setExamDate(LocalDate.parse("2024-12-15"));
    enrollment.setConsentGiven(true);

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
             "parents": []
           },
           "status": "PENDING",
           "enrollmentDate": "2024-12-15T10:15:30Z",
           "gradeToEnroll": "FIRST",
           "knownThrough": "OT",
           "examDate": "2024-12-15",
           "consentGiven": true,
           "whatsappNotification": false
         }
        """;

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

    EnrollmentEntity enrollment = new EnrollmentEntity();
    enrollment.setId(1L);
    enrollment.setStudent(student);
    enrollment.setStatus(ProcessStatus.PENDING);
    enrollment.setEnrollmentDate(Instant.parse("2024-12-15T10:15:30Z"));
    enrollment.setGradeToEnroll(Grades.FIRST);
    enrollment.setKnownThrough(KnownThrough.OT);
    enrollment.setExamDate(LocalDate.parse("2024-12-15"));
    enrollment.setConsentGiven(true);

    assertThat(json.parse(expected)).isEqualTo(enrollment);
    assertThat(json.parseObject(expected).getId()).isEqualTo(1);
    assertThat(json.parseObject(expected).getEnrollmentDate()).isEqualTo("2024-12-15T10:15:30Z");
  }
}
