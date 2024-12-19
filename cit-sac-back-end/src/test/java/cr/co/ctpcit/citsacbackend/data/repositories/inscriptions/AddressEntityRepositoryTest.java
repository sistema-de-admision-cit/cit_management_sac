package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.AddressEntity;
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
@DataJpaTest(showSql = true)
class AddressEntityRepositoryTest {

  @Autowired
  private AddressEntityRepository addressEntityRepository;
  @Autowired
  private PersonEntityRepository personEntityRepository;

  private AddressEntity addressEntity;

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

    //Create address
    addressEntity = new AddressEntity();
    addressEntity.setCountry("Costa Rica");
    addressEntity.setProvince("San Jose");
    addressEntity.setCity("San Jose");
    addressEntity.setDistrict("Pavas");
    addressEntity.setAddressInfo("100 meters south of the church");
    addressEntity.setParent(parentEntity);
  }

  @Test
  void testCreateAddress() {
    AddressEntity savedAddressEntity = addressEntityRepository.save(addressEntity);

    assertNotNull(savedAddressEntity);
    assertNotNull(savedAddressEntity.getId());
    assertEquals(addressEntity.getCountry(), savedAddressEntity.getCountry());
    assertEquals(addressEntity.getProvince(), savedAddressEntity.getProvince());
    assertEquals(addressEntity.getCity(), savedAddressEntity.getCity());
    assertEquals(addressEntity.getDistrict(), savedAddressEntity.getDistrict());
    assertEquals(addressEntity.getAddressInfo(), savedAddressEntity.getAddressInfo());
    assertEquals(addressEntity.getParent(), savedAddressEntity.getParent());
  }

  @Test
  void testUpdateAddress() {
    AddressEntity savedAddressEntity = addressEntityRepository.save(addressEntity);

    assertNotNull(savedAddressEntity);
    assertNotNull(savedAddressEntity.getId());
    assertEquals(addressEntity.getCountry(), savedAddressEntity.getCountry());
    assertEquals(addressEntity.getProvince(), savedAddressEntity.getProvince());
    assertEquals(addressEntity.getCity(), savedAddressEntity.getCity());
    assertEquals(addressEntity.getDistrict(), savedAddressEntity.getDistrict());
    assertEquals(addressEntity.getAddressInfo(), savedAddressEntity.getAddressInfo());
    assertEquals(addressEntity.getParent(), savedAddressEntity.getParent());

    savedAddressEntity.setCountry("Panama");
    savedAddressEntity.setProvince("Panama");
    savedAddressEntity.setCity("Panama");
    savedAddressEntity.setDistrict("Panama");
    savedAddressEntity.setAddressInfo("100 meters south of the church in Panama");

    AddressEntity updatedAddressEntity = addressEntityRepository.save(savedAddressEntity);

    assertNotNull(updatedAddressEntity);
    assertNotNull(updatedAddressEntity.getId());
    assertEquals(savedAddressEntity.getCountry(), updatedAddressEntity.getCountry());
    assertEquals(savedAddressEntity.getProvince(), updatedAddressEntity.getProvince());
    assertEquals(savedAddressEntity.getCity(), updatedAddressEntity.getCity());
    assertEquals(savedAddressEntity.getDistrict(), updatedAddressEntity.getDistrict());
    assertEquals(savedAddressEntity.getAddressInfo(), updatedAddressEntity.getAddressInfo());
    assertEquals(savedAddressEntity.getParent(), updatedAddressEntity.getParent());
  }

  @Test
  void testFindAddressById() {
    AddressEntity savedAddressEntity = addressEntityRepository.save(addressEntity);

    assertNotNull(savedAddressEntity);
    assertNotNull(savedAddressEntity.getId());
    assertEquals(addressEntity.getCountry(), savedAddressEntity.getCountry());
    assertEquals(addressEntity.getProvince(), savedAddressEntity.getProvince());
    assertEquals(addressEntity.getCity(), savedAddressEntity.getCity());
    assertEquals(addressEntity.getDistrict(), savedAddressEntity.getDistrict());
    assertEquals(addressEntity.getAddressInfo(), savedAddressEntity.getAddressInfo());
    assertEquals(addressEntity.getParent(), savedAddressEntity.getParent());

    AddressEntity foundAddressEntity =
        addressEntityRepository.findById(savedAddressEntity.getId()).orElse(null);

    assertNotNull(foundAddressEntity);
    assertNotNull(foundAddressEntity.getId());
    assertEquals(savedAddressEntity.getCountry(), foundAddressEntity.getCountry());
    assertEquals(savedAddressEntity.getProvince(), foundAddressEntity.getProvince());
    assertEquals(savedAddressEntity.getCity(), foundAddressEntity.getCity());
    assertEquals(savedAddressEntity.getDistrict(), foundAddressEntity.getDistrict());
    assertEquals(savedAddressEntity.getAddressInfo(), foundAddressEntity.getAddressInfo());
    assertEquals(savedAddressEntity.getParent(), foundAddressEntity.getParent());
  }

  @AfterEach
  void tearDown() {
    addressEntityRepository.deleteAll();
    personEntityRepository.deleteAll();
  }
}
