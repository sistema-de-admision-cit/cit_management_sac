package cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.PersonDto;

/**
 * A utility class to map {@link PersonEntity} to {@link PersonDto} and vice versa.
 */
public class PersonMapper {

  /**
   * Converts a {@link PersonDto} to a {@link PersonEntity}.
   *
   * @param person the {@link PersonDto} to be converted
   * @return a {@link PersonEntity} representing the converted data
   */
  public static PersonEntity convertToEntity(PersonDto person) {
    return PersonEntity.builder().id(person.id()).firstName(person.firstName())
        .firstSurname(person.firstSurname()).secondSurname(person.secondSurname())
        .idType(person.idType()).idNumber(person.idNumber()).build();
  }

  /**
   * Converts a {@link PersonEntity} to a {@link PersonDto}.
   *
   * @param person the {@link PersonEntity} to be converted
   * @return a {@link PersonDto} representing the converted data
   */
  public static PersonDto convertToDto(PersonEntity person) {
    return PersonDto.builder()
        .id(person.getId())
        .firstName(person.getFirstName())
        .firstSurname(person.getFirstSurname())
        .secondSurname(person.getSecondSurname())
        .idType(person.getIdType())
        .idNumber(person.getIdNumber()).build();
  }
}
