package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.TestProvider;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(showSql = true)
class DocumentRepositoryTest {
  @Autowired
  private EnrollmentRepository enrollmentRepository;
  @Autowired
  private PersonRepository personRepository;
  @Autowired
  private DocumentRepository documentRepository;

  private DocumentEntity documentEntity;

  @BeforeEach
  void setUp() {
    //Create parent
    ParentEntity parentEntity = TestProvider.provideParent();

    personRepository.save(parentEntity.getParentPerson());

    //Create student
    StudentEntity studentEntity = TestProvider.provideStudent();

    personRepository.save(studentEntity.getStudentPerson());
    parentEntity.addStudent(studentEntity);

    //Create enrollment
    EnrollmentEntity enrollmentEntity = TestProvider.provideEnrollment();
    enrollmentEntity.setStudent(studentEntity);

    enrollmentRepository.save(enrollmentEntity);

    //Create document
    documentEntity = TestProvider.provideDocument();

    enrollmentEntity.addDocument(documentEntity);
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
