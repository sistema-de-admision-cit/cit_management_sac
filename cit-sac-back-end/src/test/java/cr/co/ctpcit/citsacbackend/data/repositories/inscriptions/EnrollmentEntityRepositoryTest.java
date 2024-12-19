package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.ParentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(showSql = true)
class EnrollmentEntityRepositoryTest {

  @Autowired
  private EnrollmentEntityRepository enrollmentEntityRepository;
  @Autowired
  private PersonEntityRepository personEntityRepository;
  @Autowired
  private StudentEntityRepository studentEntityRepository;

  private EnrollmentEntity enrollmentEntity;

  @BeforeEach
  void setUp() {
    //Create parent
    PersonEntity parentPersonEntity = new PersonEntity();
    parentPersonEntity.setFirstName("John");
    parentPersonEntity.setFirstSurname("Doe");
    parentPersonEntity.setSecondSurname("Smith");
    parentPersonEntity.setIdType(IdType.CC);
    parentPersonEntity.setIdNumber("123456789");

    ParentEntity parentEntity = new ParentEntity();
    parentEntity.setRelationship(Relationship.F);
    parentEntity.setEmail("johndoe@mtmail.com");
    parentEntity.setPhoneNumber("88889999");

    parentPersonEntity.addParent(parentEntity);

    personEntityRepository.save(parentPersonEntity);

    //Create student
    PersonEntity studentPersonEntity = new PersonEntity();
    studentPersonEntity.setFirstName("Mark");
    studentPersonEntity.setFirstSurname("Doe");
    studentPersonEntity.setSecondSurname("Johnson");
    studentPersonEntity.setIdType(IdType.CC);
    studentPersonEntity.setIdNumber("987654321");

    StudentEntity studentEntity = new StudentEntity();
    studentEntity.setBirthDate(LocalDate.parse("2000-01-01"));
    studentEntity.setHasAccommodations(true);
    studentEntity.setPreviousSchool("Manhattan High School");

    studentPersonEntity.addStudent(studentEntity);

    personEntityRepository.save(studentPersonEntity);
    parentEntity.addStudent(studentEntity);

    //Create enrollment
    enrollmentEntity = new EnrollmentEntity();
    enrollmentEntity.setStudent(studentEntity);
    enrollmentEntity.setStatus(ProcessStatus.PENDING);
    enrollmentEntity.setEnrollmentDate(Instant.now());
    enrollmentEntity.setGradeToEnroll(Grades.FIRST);
    enrollmentEntity.setKnownThrough(KnownThrough.OT);
    enrollmentEntity.setExamDate(LocalDate.parse("2021-01-01"));
    enrollmentEntity.setConsentGiven(true);
    enrollmentEntity.setWhatsappNotification(true);
  }

  @Test
  void testSaveEnrollmentEntity() {
    EnrollmentEntity savedEnrollmentEntity = enrollmentEntityRepository.save(enrollmentEntity);

    assertNotNull(savedEnrollmentEntity);
    assertNotNull(savedEnrollmentEntity.getId());
    assertEquals(enrollmentEntity, savedEnrollmentEntity);
  }

  @Test
  void testFindEnrollmentEntityById() {
    EnrollmentEntity savedEnrollmentEntity = enrollmentEntityRepository.save(enrollmentEntity);
    EnrollmentEntity foundEnrollmentEntity =
        enrollmentEntityRepository.findById(savedEnrollmentEntity.getId()).orElse(null);

    assertNotNull(foundEnrollmentEntity);
    assertEquals(enrollmentEntity, foundEnrollmentEntity);
  }

  @AfterEach
  void tearDown() {
    enrollmentEntityRepository.deleteAll();
    studentEntityRepository.deleteAll();
    personEntityRepository.deleteAll();
  }
}
