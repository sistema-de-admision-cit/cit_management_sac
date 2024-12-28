package cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.PersonDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonMapperTest {
  @Test
  void testConvertToEntity() {
    // Arrange
    PersonDto person = new PersonDto(1L, "John", "Doe", "Smith", IdType.CC, "123456789");

    // Act
    PersonEntity personEntity = PersonMapper.convertToEntity(person);

    // Assert
    assertNotNull(personEntity);
    assertEquals(person.id(), personEntity.getId());
    assertEquals(person.firstName(), personEntity.getFirstName());
    assertEquals(person.firstSurname(), personEntity.getFirstSurname());
    assertEquals(person.secondSurname(), personEntity.getSecondSurname());
    assertEquals(person.idType(), personEntity.getIdType());
    assertEquals(person.idNumber(), personEntity.getIdNumber());
  }

  @Test
  void testConvertToDto() {
    // Arrange
    PersonEntity personEntity =
        new PersonEntity();
    personEntity.setId(1L);
    personEntity.setFirstName("John");
    personEntity.setFirstSurname("Doe");
    personEntity.setSecondSurname("Smith");
    personEntity.setIdType(IdType.CC);
    personEntity.setIdNumber("123456789");

    // Act
    PersonDto person = PersonMapper.convertToDto(personEntity);

    // Assert
    assertNotNull(person);
    assertEquals(personEntity.getId(), person.id());
    assertEquals(personEntity.getFirstName(), person.firstName());
    assertEquals(personEntity.getFirstSurname(), person.firstSurname());
    assertEquals(personEntity.getSecondSurname(), person.secondSurname());
    assertEquals(personEntity.getIdType(), person.idType());
    assertEquals(personEntity.getIdNumber(), person.idNumber());
  }
}
