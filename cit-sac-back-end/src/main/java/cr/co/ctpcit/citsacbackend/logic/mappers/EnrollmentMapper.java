package cr.co.ctpcit.citsacbackend.logic.mappers;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.EnrollmentDto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class EnrollmentMapper {
    public static EnrollmentDto convertToDto(EnrollmentEntity enrollmentEntity) {
        return EnrollmentDto.builder()
                .status(enrollmentEntity.getStatus())
                .enrollmentDate(LocalDateTime.ofInstant(enrollmentEntity.getEnrollmentDate(), ZoneId.systemDefault()))
                .gradeToEnroll(enrollmentEntity.getGradeToEnroll())
                .knownThrough(enrollmentEntity.getKnownThrough())
                .examDate(enrollmentEntity.getExamDate())
                .consentGiven(enrollmentEntity.getConsentGiven())
                .whatsappNotification(enrollmentEntity.getWhatsappNotification())
                .build();
    }

    public static List<EnrollmentDto> convertToDtoList(List<EnrollmentEntity> enrollmentEntities) {
        return enrollmentEntities
                .stream()
                .map(EnrollmentMapper::convertToDto)
                .toList();
    }
}
