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
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(showSql = true)
class EnrollmentRepositoryTest {

  @Autowired
  private EnrollmentRepository enrollmentRepository;
  @Autowired
  private PersonRepository personRepository;
  @Autowired
  private StudentRepository studentRepository;

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

    personRepository.save(parentPersonEntity);

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

    personRepository.save(studentPersonEntity);
    parentEntity.addStudent(studentEntity);

    //Create enrollment
    enrollmentEntity = new EnrollmentEntity();
    enrollmentEntity.setStudent(studentEntity);
    enrollmentEntity.setStatus(ProcessStatus.PENDING);
    enrollmentEntity.setEnrollmentDate(LocalDateTime.now());
    enrollmentEntity.setGradeToEnroll(Grades.FIRST);
    enrollmentEntity.setKnownThrough(KnownThrough.OT);
    enrollmentEntity.setExamDate(LocalDate.parse("2021-01-01"));
    enrollmentEntity.setConsentGiven(true);
    enrollmentEntity.setWhatsappNotification(true);
  }

  @Test
  void testSaveEnrollmentEntity() {
    EnrollmentEntity savedEnrollmentEntity = enrollmentRepository.save(enrollmentEntity);

    assertNotNull(savedEnrollmentEntity);
    assertNotNull(savedEnrollmentEntity.getId());
    assertEquals(enrollmentEntity, savedEnrollmentEntity);
  }

  @Test
  void testFindEnrollmentEntityById() {
    EnrollmentEntity savedEnrollmentEntity = enrollmentRepository.save(enrollmentEntity);
    EnrollmentEntity foundEnrollmentEntity =
        enrollmentRepository.findById(savedEnrollmentEntity.getId()).orElse(null);

    assertNotNull(foundEnrollmentEntity);
    assertEquals(enrollmentEntity, foundEnrollmentEntity);
  }

  @Test
  void findAllByStudentEntity() {
    EnrollmentEntity savedEnrollmentEntity = enrollmentRepository.save(enrollmentEntity);

    List<EnrollmentEntity> foundEnrollmentEntities =
        enrollmentRepository.findAllByStudent(savedEnrollmentEntity.getStudent());

    assertNotNull(foundEnrollmentEntities);
    assertEquals(enrollmentEntity, foundEnrollmentEntities.getFirst());
  }

  @AfterEach
  void tearDown() {
    enrollmentRepository.deleteAll();
    studentRepository.deleteAll();
    personRepository.deleteAll();
  }
}
