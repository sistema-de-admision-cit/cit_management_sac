package cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.PersonDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.StudentDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {
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

    StudentEntity studentEntity = new StudentEntity();
    studentEntity.setId(1L);
    studentEntity.setPerson(personEntity);
    studentEntity.setPreviousSchool("School");
    studentEntity.setHasAccommodations(true);
    studentEntity.setBirthDate(LocalDate.parse("2000-01-01"));
    studentEntity.setParents(new ArrayList<>());

    // Act
    StudentDto student = StudentMapper.convertToDto(studentEntity);

    // Assert
    assertNotNull(student);
    assertEquals(studentEntity.getId(), student.id());
    assertEquals(studentEntity.getPerson().getId(), student.person().id());
    assertEquals(studentEntity.getPreviousSchool(), student.previousSchool());
    assertEquals(studentEntity.getHasAccommodations(), student.hasAccommodations());
    assertEquals(studentEntity.getBirthDate(), student.birthDate());
  }

  @Test
  void testConvertToEntity() {
    // Arrange
    StudentDto student =
        new StudentDto(1L, new PersonDto(1L, "John", "Doe", "Smith", IdType.CC, "123456789"),
            LocalDate.parse("2000-01-01"), "School", true, new ArrayList<>());

    // Act
    StudentEntity studentEntity = StudentMapper.convertToEntity(student);

    // Assert
    assertNotNull(studentEntity);
    assertEquals(student.id(), studentEntity.getId());
    assertEquals(student.previousSchool(), studentEntity.getPreviousSchool());
    assertEquals(student.hasAccommodations(), studentEntity.getHasAccommodations());
    assertEquals(student.birthDate(), studentEntity.getBirthDate());
  }
}
