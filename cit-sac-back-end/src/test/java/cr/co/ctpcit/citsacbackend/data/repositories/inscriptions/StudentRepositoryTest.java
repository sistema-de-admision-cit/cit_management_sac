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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(showSql = true)
class StudentRepositoryTest {
  @Autowired
  private StudentRepository studentRepository;
  @Autowired
  private PersonRepository personRepository;

  private StudentEntity studentEntity;
  private PersonEntity personEntity;
  @Autowired
  private ParentRepository parentRepository;

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
  void testSaveNewStudentEntity() {
    PersonEntity savedPersonEntity = personRepository.save(personEntity);

    assertNotNull(savedPersonEntity);
    assertNotNull(savedPersonEntity.getId());
    assertEquals(personEntity, savedPersonEntity);
    assertNotNull(savedPersonEntity.getStudent());
    assertNotNull(studentEntity.getStudentPerson());
  }

  @Test
  void testFindStudentEntityByPersonId() {
    PersonEntity savedPersonEntity = personRepository.save(personEntity);
    StudentEntity savedStudentEntity =
        studentRepository.findById(savedPersonEntity.getId()).orElse(null);

    assertNotNull(savedStudentEntity);
    assertEquals(studentEntity, savedStudentEntity);
  }

  @Test
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
    personRepository.save(parentPersonEntity); //Save Parent

    PersonEntity savedStudentPersonEntity = personRepository.save(personEntity);

    parentEntity.addStudent(savedStudentPersonEntity.getStudent());

    StudentEntity savedStudentEntity =
        studentRepository.findById(savedStudentPersonEntity.getId()).orElse(null);


    assertNotNull(savedStudentEntity);
    assertEquals(studentEntity, savedStudentEntity);
    assertNotNull(savedStudentEntity.getParents());
    assertEquals(1, savedStudentEntity.getParents().size());
  }

  @Test
  void shouldFindStudentByPersonIdNumber() {
    PersonEntity savedPersonEntity = personRepository.save(personEntity);
    StudentEntity savedStudentEntity =
        studentRepository.findStudentEntityByStudentPerson_IdNumber(savedPersonEntity.getIdNumber())
            .orElse(null);

    assertNotNull(savedStudentEntity);
    assertEquals(studentEntity, savedStudentEntity);
  }

  @Test
  void shouldAddStudentToExistingParent() {
    //Find Parent
    ParentEntity parentEntity = parentRepository.findById(10L).orElse(null);
    assertNotNull(parentEntity);

    //Create Student
    parentEntity.addStudent(studentEntity);

    //Save Student
    studentEntity = studentRepository.save(studentEntity);
    assertNotNull(studentEntity);
    assertNotNull(studentEntity.getId());

    //Find Parent-Student relation
    StudentEntity savedStudentEntity =
        studentRepository.findById(studentEntity.getId()).orElse(null);
    assertThat(savedStudentEntity).isNotNull();
    assertEquals(1, savedStudentEntity.getParents().size());
  }

  @Test
  void testFindStudentByValue() {
    List<StudentEntity> students = studentRepository.findAllByValue("and");

    assertThat(students.size()).isEqualTo(4);
  }

  @Test
  void testCountStudentsByValue() {
    Long count = studentRepository.countEnrollmentsInProcessByValue("and");

    assertThat(count).isEqualTo(4);
  }

  @Test
  void testFindAllWithEnrollmentsInProcess() {
    // Create a new student and save it
    Pageable pageable = Pageable.ofSize(50);
    Page<StudentEntity> students = studentRepository.findAllWithEnrollmentsInProcess(pageable);

    assertThat(students.getContent().size()).isEqualTo(35);
  }

  @AfterEach
  void tearDown() {
    personRepository.deleteAll();
  }
}
