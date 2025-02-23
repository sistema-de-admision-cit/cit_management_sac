package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {
  List<EnrollmentEntity> findAllByStudent(@NotNull StudentEntity student);

  List<EnrollmentEntity> findAllByStudentIn(List<StudentEntity> students);

  @Modifying
  @Transactional
  @Query("UPDATE EnrollmentEntity e SET e.examDate = :examDate WHERE e.id = :id")
  void updateEnrollmentExamDate(Long id, LocalDate examDate);

  @Modifying
  @Transactional
  @Query("UPDATE EnrollmentEntity e SET e.status = :status WHERE e.id = :id")
  void updateEnrollmentStatus(Long id, ProcessStatus status);

  @Modifying
  @Transactional
  @Query(
      "UPDATE EnrollmentEntity e SET e.whatsappNotification = :whatsappPermission WHERE e.id = :id")
  void updateEnrollmentWhatsappPermission(Long id, Boolean whatsappPermission);

  @Procedure(name = "usp_update_enrollment_and_log")
  void usp_update_enrollment_and_log(@Param("p_enrollment_id") Long enrollmentId,
      @Param("p_new_status") String newStatus, @Param("p_new_exam_date") Date newExamDate,
      @Param("p_new_whatsapp_permission") Boolean newWhatsappPermission,
      @Param("p_comment") String comment, @Param("p_changed_by") Integer changedBy);
}
