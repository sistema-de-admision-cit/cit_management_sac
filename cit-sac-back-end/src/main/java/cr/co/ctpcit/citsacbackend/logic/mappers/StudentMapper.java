package cr.co.ctpcit.citsacbackend.logic.mappers;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.StudentEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;

public class StudentMapper {
    public static StudentDto convertToDto(StudentEntity studentEntity) {
        return StudentDto.builder()
                .firstName(studentEntity.getFirstName())
                .firstSurname(studentEntity.getFirstSurname())
                .secondSurname(studentEntity.getSecondSurname())
                .birthDate(studentEntity.getBirthDate())
                .idType(studentEntity.getIdType())
                .idNumber(studentEntity.getIdNumber())
                .previousSchool(studentEntity.getPreviousSchool())
                .hasAccommodations(studentEntity.getHasAccommodations())
                .build();
    }
}
