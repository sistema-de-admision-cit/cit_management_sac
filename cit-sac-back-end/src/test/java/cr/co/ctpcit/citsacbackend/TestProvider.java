package cr.co.ctpcit.citsacbackend;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.*;
import cr.co.ctpcit.citsacbackend.data.enums.*;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.SystemConfigDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.UpdateContactInfoConfigsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.UpdateWeightsConfigsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.AddressDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.ParentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.PersonDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.StudentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionDto;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionOptionDto;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;

public class TestProvider {

  public static PersonEntity provideStudentPerson() {
    PersonEntity person = new PersonEntity();
    person.setId(11L);
    person.setFirstName("Andrés");
    person.setFirstSurname("Rodríguez");
    person.setSecondSurname("Morales");
    person.setIdType(IdType.CC);
    person.setIdNumber("200123654");
    person.setFullSurname("Rodríguez Morales");

    return person;
  }

  public static PersonEntity provideParentPerson() {
    PersonEntity person = new PersonEntity();
    person.setId(1L);
    person.setFirstName("Carlos");
    person.setFirstSurname("Rodríguez");
    person.setSecondSurname("Morales");
    person.setIdType(IdType.CC);
    person.setIdNumber("900321654");
    person.setFullSurname("Rodríguez Morales");

    return person;
  }

  public static AddressEntity provideAddress() {
    AddressEntity address = new AddressEntity();
    address.setId(1L);
    address.setCountry("Costa Rica");
    address.setProvince("San José");
    address.setCity("San José");
    address.setDistrict("Carmen");
    address.setAddressInfo("Avenida Central 100");

    return address;
  }

  public static StudentEntity provideStudent() {
    StudentEntity student = new StudentEntity();
    student.setId(11L);
    student.setStudentPerson(provideStudentPerson());
    student.setBirthDate(LocalDate.parse("2010-03-12"));
    student.setPreviousSchool("Escuela La Sabana");
    student.setHasAccommodations(false);

    return student;
  }

  public static ParentEntity provideParent() {
    ParentEntity parent = new ParentEntity();
    parent.setId(1L);
    parent.setParentPerson(provideParentPerson());
    parent.setEmail("carlos.rod@example.com");
    parent.setPhoneNumber("876543210");
    parent.setRelationship(Relationship.F);
    parent.setDaiExam(null);

    return parent;
  }

  public static EnrollmentEntity provideEnrollment() {
    EnrollmentEntity enrollment = new EnrollmentEntity();
    enrollment.setId(1L);
    enrollment.setStatus(ProcessStatus.PENDING);
    enrollment.setEnrollmentDate(Instant.parse("2024-12-15T10:15:30Z"));
    enrollment.setGradeToEnroll(Grades.FIRST);
    enrollment.setKnownThrough(KnownThrough.OT);
    enrollment.setExamDate(LocalDate.parse("2024-12-15"));
    enrollment.setConsentGiven(true);

    return enrollment;
  }

  public static DocumentEntity provideDocument() {
    DocumentEntity document = new DocumentEntity();
    document.setId(1L);
    document.setDocumentName("Other Document Sofía");
    document.setDocType(DocType.OT);
    document.setDocumentUrl("/home/user/docs/sofia_other.pdf");

    return document;
  }

  public static PersonDto provideParentPersonDto() {
    return new PersonDto(1L, "Carlos", "Rodríguez", "Morales", IdType.CC, "900321654");
  }

  public static ParentDto provideParentDto() {
    return new ParentDto(1L, provideParentPersonDto(), "876543210", "carlos.rod@example.com",
        Relationship.F, new ArrayList<>());
  }

  public static PersonDto provideStudentPersonDto() {
    return new PersonDto(11L, "Andrés", "Rodríguez", "Morales", IdType.CC, "200123654");
  }

  public static StudentDto provideStudentDto() {
    return new StudentDto(11L, provideStudentPersonDto(), LocalDate.parse("2010-03-12"),
        "Escuela La Sabana", false, new ArrayList<>());
  }

  public static AddressDto provideAddressDto() {
    return new AddressDto(1L, "Costa Rica", "San José", "San José", "Carmen",
        "Avenida Central 100");
  }

  public static QuestionDto provideQuestionDto() {
    return new QuestionDto(2L, QuestionType.ACA, "¿Como se calcula el area de un circulo?", null,
        Grades.FIRST, QuestionLevel.EASY, SelectionType.SINGLE, false, getQuestionOptions());
  }

  private static ArrayList<QuestionOptionDto> getQuestionOptions() {
    ArrayList<QuestionOptionDto> options = new ArrayList<>();
    options.add(new QuestionOptionDto(1L, true, "π * radio^2"));
    options.add(new QuestionOptionDto(2L, false, "2 * radio"));
    options.add(new QuestionOptionDto(3L, false, "π * diámetro"));
    options.add(new QuestionOptionDto(4L, false, "radio * altura"));
    return options;
  }

  public static UpdateWeightsConfigsDto provideUpdateWeightsConfigsDto() {
    return new UpdateWeightsConfigsDto(0.4, 0.4, 0.2);
  }

  public static UpdateContactInfoConfigsDto provideUpdateContactInfoConfigsDto() {
    return new UpdateContactInfoConfigsDto("contactocit@ctpcit.co.cr",
        "notificaciones@ctpcit.co.cr", "88090041", "22370186", "ComplejoEducativoCIT",
        "ComplejoEducativoCIT");
  }

  public static SystemConfigDto provideSystemConfigDto() {
    return new SystemConfigDto(1, Configurations.EMAIL_CONTACT, "contactocit@ctpcit.co.cr");
  }
}

