package cr.co.ctpcit.citsacbackend.logic.mappers;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.ParentsGuardianEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.ParentsGuardianDto;

import java.util.List;

public class ParentGuardianMapper {
    public static ParentsGuardianDto convertToDto(ParentsGuardianEntity parentGuardianEntity) {
        return ParentsGuardianDto.builder()
                .firstName(parentGuardianEntity.getFirstName())
                .firstSurname(parentGuardianEntity.getFirstSurname())
                .secondSurname(parentGuardianEntity.getSecondSurname())
                .idType(parentGuardianEntity.getIdType())
                .idNumber(parentGuardianEntity.getIdNumber())
                .phoneNumber(parentGuardianEntity.getPhoneNumber())
                .email(parentGuardianEntity.getEmail())
                .homeAddress(parentGuardianEntity.getHomeAddress())
                .relationship(parentGuardianEntity.getRelationship())
                .build();
    }

    public static List<ParentsGuardianDto> convertToDtoList(List<ParentsGuardianEntity> parentsGuardianEntities) {
        return parentsGuardianEntities
                .stream()
                .map(ParentGuardianMapper::convertToDto)
                .toList();
    }
}
