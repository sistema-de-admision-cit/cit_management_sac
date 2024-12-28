package cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.ParentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import cr.co.ctpcit.citsacbackend.data.enums.Relationship;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.ParentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.PersonDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ParentMapperTest {

  @Test
  void testConvertToEntity() {
    // Arrange
    ParentDto parent =
        new ParentDto(1L, new PersonDto(1L, "John", "Doe", "Smith", IdType.CC, "123456789"), "88889999", "johndoe@mtmail.com",
            Relationship.F, new ArrayList<>());

    // Act
    ParentEntity parentEntity = ParentMapper.convertToEntity(parent);

    // Assert
    assertNotNull(parentEntity);
    assertEquals(parent.phoneNumber(), parentEntity.getPhoneNumber());
    assertEquals(parent.email(), parentEntity.getEmail());
    assertEquals(parent.relationship(), parentEntity.getRelationship());
  }

  @Test
  void testConvertToDto() {
    // Arrange
    PersonEntity personEntity = new PersonEntity();
    personEntity.setId(1L);
    personEntity.setFirstName("John");
    personEntity.setFirstSurname("Doe");
    personEntity.setSecondSurname("Smith");
    personEntity.setIdType(IdType.CC);
    personEntity.setIdNumber("123456789");

    ParentEntity parentEntity = new ParentEntity();
    parentEntity.setId(1L);
    parentEntity.setPerson(personEntity);
    parentEntity.setPhoneNumber("88889999");
    parentEntity.setEmail("johndoe@mtmail.com");
    parentEntity.setRelationship(Relationship.F);

    // Act
    ParentDto parent = ParentMapper.convertToDto(parentEntity);

    // Assert
    assertNotNull(parent);
    assertEquals(parentEntity.getId(), parent.id());
    assertEquals(parentEntity.getPerson().getId(), parent.person().id());
    assertEquals(parentEntity.getPhoneNumber(), parent.phoneNumber());
    assertEquals(parentEntity.getEmail(), parent.email());
    assertEquals(parentEntity.getRelationship(), parent.relationship());
  }
}
