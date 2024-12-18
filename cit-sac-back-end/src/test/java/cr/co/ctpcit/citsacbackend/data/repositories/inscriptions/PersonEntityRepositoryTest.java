package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(showSql = true)
class PersonEntityRepositoryTest {
  @Autowired
  private PersonEntityRepository personEntityRepository;

  private PersonEntity personEntity;

  @BeforeEach
  void setUp() {
    personEntity = new PersonEntity();
    personEntity.setFirstName("John");
    personEntity.setFirstSurname("Doe");
    personEntity.setSecondSurname("Smith");
    personEntity.setIdType(IdType.CC);
    personEntity.setIdNumber("123456789");
  }

  @Test
  void testSavePersonEntity() {
    PersonEntity savedPersonEntity = personEntityRepository.save(personEntity);
    assertNotNull(savedPersonEntity);
    assertNotNull(savedPersonEntity.getId());
    assertEquals(personEntity, savedPersonEntity);
  }

  @Test
  void testFindPersonEntityById() {
    PersonEntity savedPersonEntity = personEntityRepository.save(personEntity);
    assertNotNull(savedPersonEntity);
    assertNotNull(savedPersonEntity.getId());
    assertEquals(personEntity, savedPersonEntity);

    PersonEntity foundPersonEntity =
        personEntityRepository.findById(savedPersonEntity.getId()).orElse(null);
    assertNotNull(foundPersonEntity);
  }

  @AfterEach
  void tearDown() {
    personEntityRepository.deleteAll();
  }
}
