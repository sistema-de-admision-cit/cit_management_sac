package cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.PersonDto;

public class PersonMapper {
  public static PersonEntity convertToEntity(PersonDto person) {
    return PersonEntity.builder()
        .id(person.id())
        .firstName(person.firstName())
        .firstSurname(person.firstSurname())
        .secondSurname(person.secondSurname())
        .idType(person.idType())
        .idNumber(person.idNumber())
        .build();
  }
}
