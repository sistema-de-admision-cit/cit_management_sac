package cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import cr.co.ctpcit.citsacbackend.data.enums.KnownThrough;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.PersonDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.StudentDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EnrollmentMapperTest {
  @Test
  void testConvertToDto() {
    //Arrange
    PersonEntity personEntity = new PersonEntity();
    personEntity.setId(1L);
    personEntity.setFirstName("John");
    personEntity.setFirstSurname("Doe");
    personEntity.setSecondSurname("Smith");
    personEntity.setIdType(IdType.CC);
    personEntity.setIdNumber("123456789");

    StudentEntity studentEntity = new StudentEntity();
    studentEntity.setId(1L);
    studentEntity.setStudentPerson(personEntity);
    studentEntity.setPreviousSchool("School");
    studentEntity.setHasAccommodations(true);
    studentEntity.setBirthDate(LocalDate.parse("2000-01-01"));
    studentEntity.setParents(new ArrayList<>());

    EnrollmentEntity enrollmentEntity = new EnrollmentEntity();
    enrollmentEntity.setId(1L);
    enrollmentEntity.setStudent(studentEntity);
    enrollmentEntity.setStatus(ProcessStatus.PENDING);
    enrollmentEntity.setGradeToEnroll(Grades.FIRST);
    enrollmentEntity.setKnownThrough(KnownThrough.OT);
    enrollmentEntity.setExamDate(LocalDate.parse("2000-01-01"));
    enrollmentEntity.setConsentGiven(true);
    enrollmentEntity.setWhatsappNotification(true);
    enrollmentEntity.setDocuments(new ArrayList<>());

    //Act
    EnrollmentDto enrollment = EnrollmentMapper.convertToDto(enrollmentEntity);

    //Assert
    assertNotNull(enrollment);
    assertEquals(enrollmentEntity.getId(), enrollment.id());
    assertEquals(enrollmentEntity.getStudent().getId(), enrollment.student().id());
    assertEquals(enrollmentEntity.getStatus(), enrollment.status());
    assertEquals(enrollmentEntity.getGradeToEnroll(), enrollment.gradeToEnroll());
    assertEquals(enrollmentEntity.getKnownThrough(), enrollment.knownThrough());
    assertEquals(enrollmentEntity.getExamDate(), enrollment.examDate());
    assertEquals(enrollmentEntity.getConsentGiven(), enrollment.consentGiven());
    assertEquals(enrollmentEntity.getWhatsappNotification(), enrollment.whatsappNotification());
  }

  @Test
  void testConvertToEntity() {
    //Arrange
    EnrollmentDto enrollment = new EnrollmentDto(1L,
        new StudentDto(1L, new PersonDto(1L, "John", "Doe", "Smith", IdType.CC, "123456789"),
            LocalDate.parse("2000-01-01"), "School", true,new BigDecimal("8.5"), new ArrayList<>()),
        ProcessStatus.PENDING, Grades.FIRST, KnownThrough.OT, LocalDate.parse("2000-01-01"), true,
        true, new ArrayList<>());

    //Act
    EnrollmentEntity enrollmentEntity = EnrollmentMapper.convertToEntity(enrollment);

    //Assert
    assertNotNull(enrollmentEntity);
    assertEquals(enrollment.id(), enrollmentEntity.getId());
    assertEquals(enrollment.status(), enrollmentEntity.getStatus());
    assertEquals(enrollment.gradeToEnroll(), enrollmentEntity.getGradeToEnroll());
    assertEquals(enrollment.knownThrough(), enrollmentEntity.getKnownThrough());
    assertEquals(enrollment.examDate(), enrollmentEntity.getExamDate());
    assertEquals(enrollment.consentGiven(), enrollmentEntity.getConsentGiven());
    assertEquals(enrollment.whatsappNotification(), enrollmentEntity.getWhatsappNotification());
  }
}
