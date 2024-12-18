package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.ParentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import cr.co.ctpcit.citsacbackend.data.enums.Relationship;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(showSql = true)
class StudentEntityRepositoryTest {
  @Autowired
  private StudentEntityRepository studentEntityRepository;
  @Autowired
  private PersonEntityRepository personEntityRepository;

  private StudentEntity studentEntity;
  private PersonEntity personEntity;

  @BeforeEach
  void setUp() {
    personEntity = new PersonEntity();
    personEntity.setFirstName("John");
    personEntity.setFirstSurname("Doe");
    personEntity.setSecondSurname("Smith");
    personEntity.setIdType(IdType.CC);
    personEntity.setIdNumber("123456789");

    studentEntity = new StudentEntity();
    studentEntity.setBirthDate(LocalDate.parse("2000-01-01"));
    studentEntity.setHasAccommodations(true);
    studentEntity.setPreviousSchool("Manhattan High School");

    personEntity.addStudent(studentEntity);
  }

  @Test
  void testSaveStudentEntity() {
    PersonEntity savedPersonEntity = personEntityRepository.save(personEntity);

    assertNotNull(savedPersonEntity);
    assertNotNull(savedPersonEntity.getId());
    assertEquals(personEntity, savedPersonEntity);
    assertNotNull(savedPersonEntity.getStudent());
    assertNotNull(studentEntity.getPerson());
  }

  @Test
  void testFindStudentEntityById() {
    PersonEntity savedPersonEntity = personEntityRepository.save(personEntity);
    StudentEntity savedStudentEntity =
        studentEntityRepository.findById(savedPersonEntity.getId()).orElse(null);

    assertNotNull(savedStudentEntity);
    assertEquals(studentEntity, savedStudentEntity);
  }

  @Test
  @Disabled
  void testSaveParentStudentsEntity() {
    PersonEntity parentPersonEntity = new PersonEntity();
    parentPersonEntity.setFirstName("Jane");
    parentPersonEntity.setFirstSurname("Doe");
    parentPersonEntity.setSecondSurname("Smith");
    parentPersonEntity.setIdType(IdType.CC);
    parentPersonEntity.setIdNumber("987654321");

    ParentEntity parentEntity = new ParentEntity();
    parentEntity.setRelationship(Relationship.M);
    parentEntity.setEmail("janedoe@mitmail.com");
    parentEntity.setPhoneNumber("88889999");

    parentPersonEntity.addParent(parentEntity);
    PersonEntity savedParentPersonEntity = personEntityRepository.save(parentPersonEntity);
    PersonEntity savedStudentPersonEntity = personEntityRepository.save(personEntity);

    parentEntity.addStudent(savedStudentPersonEntity.getStudent());
  }

  @AfterEach
  void tearDown() {
    personEntityRepository.deleteAll();
  }
}
