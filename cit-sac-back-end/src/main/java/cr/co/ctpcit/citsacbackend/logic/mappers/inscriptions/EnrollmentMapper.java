package cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentDto;

import java.util.List;

/**
 * Mapper for {@link EnrollmentEntity} This class is used to convert an {@link EnrollmentEntity} to
 * an {@link EnrollmentDto}
 */
public class EnrollmentMapper {

  /**
   * Converts a {@link EnrollmentEntity} to an {@link EnrollmentDto}.
   *
   * @param enrollmentEntity the {@link EnrollmentEntity} to be converted
   * @return an {@link EnrollmentDto} representing the converted data
   */
  public static EnrollmentDto convertToDto(EnrollmentEntity enrollmentEntity) {
    return EnrollmentDto.builder()
        .id(enrollmentEntity.getId())
        .student(StudentMapper.convertToDto(enrollmentEntity.getStudent()))
        .status(enrollmentEntity.getStatus())
        .gradeToEnroll(enrollmentEntity.getGradeToEnroll())
        .knownThrough(enrollmentEntity.getKnownThrough())
        .examDate(enrollmentEntity.getExamDate())
        .consentGiven(enrollmentEntity.getConsentGiven())
        .whatsappNotification(enrollmentEntity.getWhatsappNotification())
        .documents(DocumentMapper.convertToDtoList(enrollmentEntity.getDocuments())).build();
  }

  /**
   * Converts a list of {@link EnrollmentEntity} objects to a list of {@link EnrollmentDto} objects.
   *
   * @param enrollmentEntities the list of {@link EnrollmentEntity} objects to be converted
   * @return a list of {@link EnrollmentDto} objects representing the converted data
   */
  public static List<EnrollmentDto> convertToDtoList(List<EnrollmentEntity> enrollmentEntities) {
    return enrollmentEntities.stream().map(EnrollmentMapper::convertToDto).toList();
  }

  /**
   * Converts a list of {@link EnrollmentDto} objects to a list of {@link EnrollmentEntity} objects.
   *
   * @param enrollments the list of {@link EnrollmentDto} objects to be converted
   * @return a list of {@link EnrollmentEntity} objects representing the converted data
   */
  public static List<EnrollmentEntity> convertToEntityList(List<EnrollmentDto> enrollments) {
    return enrollments.stream().map(EnrollmentMapper::convertToEntity).toList();
  }

  /**
   * Converts an {@link EnrollmentDto} to an {@link EnrollmentEntity}.
   *
   * @param enrollmentDto the {@link EnrollmentDto} to be converted
   * @return an {@link EnrollmentEntity} representing the converted data
   */
  public static EnrollmentEntity convertToEntity(EnrollmentDto enrollmentDto) {
    return EnrollmentEntity.builder()
        .id(enrollmentDto.id())
        .status(enrollmentDto.status())
        .gradeToEnroll(enrollmentDto.gradeToEnroll()).knownThrough(enrollmentDto.knownThrough())
        .examDate(enrollmentDto.examDate()).consentGiven(enrollmentDto.consentGiven())
        .whatsappNotification(enrollmentDto.whatsappNotification()).build();
  }
}
