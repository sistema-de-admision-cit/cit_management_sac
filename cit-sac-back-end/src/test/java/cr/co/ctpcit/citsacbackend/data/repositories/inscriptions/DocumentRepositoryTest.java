package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.*;
import cr.co.ctpcit.citsacbackend.data.enums.*;
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
class DocumentRepositoryTest {
  @Autowired
  private EnrollmentRepository enrollmentRepository;
  @Autowired
  private PersonRepository personRepository;
  @Autowired
  private StudentRepository studentRepository;
  @Autowired
  private DocumentRepository documentRepository;

  private DocumentEntity documentEntity;

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
    EnrollmentEntity enrollmentEntity = new EnrollmentEntity();
    enrollmentEntity.setStudent(studentEntity);
    enrollmentEntity.setStatus(ProcessStatus.PENDING);
    enrollmentEntity.setEnrollmentDate(Instant.now());
    enrollmentEntity.setGradeToEnroll(Grades.FIRST);
    enrollmentEntity.setKnownThrough(KnownThrough.OT);
    enrollmentEntity.setExamDate(LocalDate.parse("2021-01-01"));
    enrollmentEntity.setConsentGiven(true);
    enrollmentEntity.setWhatsappNotification(true);

    enrollmentRepository.save(enrollmentEntity);

    //Create document
    documentEntity = new DocumentEntity();
    documentEntity.setId(null);
    documentEntity.setEnrollment(enrollmentEntity);
    documentEntity.setDocumentName("Document Name");
    documentEntity.setDocType(DocType.OT);
    documentEntity.setDocumentUrl("D:/Documents/DocumentName.pdf");
  }

  @Test
  void testSaveDocumentEntity() {
    DocumentEntity savedDocumentEntity = documentRepository.save(documentEntity);
    documentEntity.setId(savedDocumentEntity.getId());

    assertNotNull(savedDocumentEntity);
    assertNotNull(savedDocumentEntity.getId());
    assertEquals(documentEntity, savedDocumentEntity);
  }

  @Test
  void testFindDocumentEntityById() {
    DocumentEntity savedDocumentEntity = documentRepository.save(documentEntity);
    documentEntity.setId(savedDocumentEntity.getId());

    assertNotNull(savedDocumentEntity);
    assertNotNull(savedDocumentEntity.getId());
    assertEquals(documentEntity, savedDocumentEntity);

    DocumentEntity foundDocumentEntity =
        documentRepository.findById(savedDocumentEntity.getId()).orElse(null);
    assertNotNull(foundDocumentEntity);
  }
}
