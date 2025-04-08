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
class ParentRepositoryTest {
  @Autowired
  private ParentRepository parentRepository;

  @Autowired
  private PersonRepository personRepository;

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
    PersonEntity savedPersonEntity = personRepository.save(personEntity);

    assertNotNull(savedPersonEntity);
    assertNotNull(savedPersonEntity.getId());
    assertEquals(personEntity, savedPersonEntity);
    assertNotNull(savedPersonEntity.getParent());
    assertNotNull(parentEntity.getParentPerson());
  }

  @Test
  void testFindParentEntityById() {
    personEntity.addParent(parentEntity);
    PersonEntity savedPersonEntity = personRepository.save(personEntity);
    ParentEntity savedParentEntity = parentRepository.findById(savedPersonEntity.getId()).orElse(null);

    assertNotNull(savedParentEntity);
    assertNotNull(savedParentEntity.getId());
    assertEquals(parentEntity, savedParentEntity);
  }

  @AfterEach
  void tearDown() {
    parentRepository.deleteAll();
    personRepository.deleteAll();
  }
}
