package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.ParentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import cr.co.ctpcit.citsacbackend.data.enums.Relationship;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(showSql = false)
class ParentEntityRepositoryTest {
  @Autowired
  private ParentEntityRepository parentEntityRepository;

  @Autowired
  private PersonEntityRepository personEntityRepository;

  private PersonEntity personEntity;
  private ParentEntity parentEntity;

  @BeforeEach
  void setUp() {
    personEntity = new PersonEntity();
    personEntity.setFirstName("John");
    personEntity.setFirstSurname("Doe");
    personEntity.setSecondSurname("Smith");
    personEntity.setIdType(IdType.CC);
    personEntity.setIdNumber("123456789");

    parentEntity = new ParentEntity();
    parentEntity.setRelationship(Relationship.F);
    parentEntity.setEmail("johndoe@mtmail.com");
    parentEntity.setPhoneNumber("88889999");
  }

  @Test
  void testSaveParentEntity() {
    personEntity.addParent(parentEntity);
    PersonEntity savedPersonEntity = personEntityRepository.save(personEntity);

    assertNotNull(savedPersonEntity);
    assertNotNull(savedPersonEntity.getId());
    assertEquals(personEntity, savedPersonEntity);
    assertNotNull(savedPersonEntity.getParent());
    assertNotNull(parentEntity.getPerson());
  }

  @Test
  void testFindParentEntityById() {
    personEntity.addParent(parentEntity);
    PersonEntity savedPersonEntity = personEntityRepository.save(personEntity);
    ParentEntity savedParentEntity = parentEntityRepository.findById(savedPersonEntity.getId()).orElse(null);

    assertNotNull(savedParentEntity);
    assertNotNull(savedParentEntity.getId());
    assertEquals(parentEntity, savedParentEntity);
  }

  @AfterEach
  void tearDown() {
    parentEntityRepository.deleteAll();
    personEntityRepository.deleteAll();
  }
}
