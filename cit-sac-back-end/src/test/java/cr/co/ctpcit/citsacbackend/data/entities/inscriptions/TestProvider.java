package cr.co.ctpcit.citsacbackend.data.entities.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.*;

import java.time.Instant;
import java.time.LocalDate;

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
}

