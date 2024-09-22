package cr.co.ctpcit.citsacbackend.logic.mappers;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.EnrollmentDto;

import java.util.List;

/**
 * Mapper for {@link EnrollmentEntity} This class is used to convert an {@link EnrollmentEntity} to
 * an {@link EnrollmentDto}
 */
public class EnrollmentMapper {
  public static EnrollmentDto convertToDto(EnrollmentEntity enrollmentEntity) {
    return EnrollmentDto.builder().id(enrollmentEntity.getId()).status(enrollmentEntity.getStatus())
        .gradeToEnroll(enrollmentEntity.getGradeToEnroll())
        .knownThrough(enrollmentEntity.getKnownThrough()).examDate(enrollmentEntity.getExamDate())
        .consentGiven(enrollmentEntity.getConsentGiven())
        .whatsappNotification(enrollmentEntity.getWhatsappNotification())
        .document(DocumentMapper.convertToDtoList(enrollmentEntity.getDocuments())).build();
  }

  public static List<EnrollmentDto> convertToDtoList(List<EnrollmentEntity> enrollmentEntities) {
    return enrollmentEntities.stream().map(EnrollmentMapper::convertToDto).toList();
  }

  public static List<EnrollmentEntity> convertToEntityList(List<EnrollmentDto> enrollments) {
    return enrollments.stream().map(EnrollmentMapper::convertToEntity).toList();
  }

  public static EnrollmentEntity convertToEntity(EnrollmentDto enrollmentDto) {
    return EnrollmentEntity.builder().status(enrollmentDto.status())
        .gradeToEnroll(enrollmentDto.gradeToEnroll()).knownThrough(enrollmentDto.knownThrough())
        .examDate(enrollmentDto.examDate()).consentGiven(enrollmentDto.consentGiven())
        .whatsappNotification(enrollmentDto.whatsappNotification()).build();
  }
}
