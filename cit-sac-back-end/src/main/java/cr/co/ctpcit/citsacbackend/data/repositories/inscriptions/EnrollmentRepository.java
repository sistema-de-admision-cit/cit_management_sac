package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {

  @Query(
      "SELECT e FROM EnrollmentEntity e WHERE e.status = 'PENDING' OR e.status = 'ELIGIBLE' OR e.status = 'INELIGIBLE'")
  Page<EnrollmentEntity> findAllEnrollmentsInProcess(Pageable pageable);

  List<EnrollmentEntity> findAllByStudent(@NotNull StudentEntity student);


  @Query(
      "SELECT e FROM EnrollmentEntity e WHERE e.student = :student AND (e.status = 'PENDING' OR e.status = 'ELIGIBLE' OR e.status = 'INELIGIBLE')")
  List<EnrollmentEntity> findAllByStudentPerson_IdNumber_ThatAreInProcess(@NotNull StudentEntity student);

  List<EnrollmentEntity> findAllByStudent_StudentPerson_IdNumber(@NotNull String idNumber);

  @Query(
      "SELECT e FROM EnrollmentEntity e WHERE e.student IN :students AND (e.status = 'PENDING' OR e.status = 'ELIGIBLE' OR e.status = 'INELIGIBLE')")
  List<EnrollmentEntity> findAllByStudentInTheListThatHasEnrollmentsInProcess(
      List<StudentEntity> students);

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

  Page<EnrollmentEntity> findAllByStatusIn(List<ProcessStatus> statuses, Pageable pageable);


  @Query("SELECT e FROM EnrollmentEntity e WHERE e.student.studentPerson.idNumber LIKE %:idNumber% AND e.status IN :statuses")
  Page<EnrollmentEntity> findByIdNumberAndStatusIn(
          @Param("idNumber") String idNumber,
          @Param("statuses") List<ProcessStatus> statuses,
          Pageable pageable
  );


  @Query("SELECT e FROM EnrollmentEntity e WHERE e.student IN :students AND e.status IN :statuses")
  Page<EnrollmentEntity> findAllByStudentsWithStatusIn(
          @Param("students") List<StudentEntity> students,
          @Param("statuses") List<ProcessStatus> statuses,
          Pageable pageable
  );

  @Query("SELECT e FROM EnrollmentEntity e WHERE e.student.studentPerson.idNumber = :idNumber AND e.status = :status")
  Optional<EnrollmentEntity> findByStudentStudentPersonIdNumberAndStatus(
          @Param("idNumber") String idNumber,
          @Param("status") ProcessStatus status);

  @Query("SELECT e FROM EnrollmentEntity e WHERE e.student.studentPerson.idNumber = :idNumber")
  Optional<EnrollmentEntity> findByStudentStudentPersonIdNumber(@Param("idNumber") String idNumber);

  @Query("SELECT e FROM EnrollmentEntity e WHERE e.student.studentPerson.idNumber = :idNumber AND e.status IN :statuses")
  Optional<EnrollmentEntity> findByStudentStudentPersonIdNumberAndStatusIn(
          @Param("idNumber") String idNumber,
          @Param("statuses") List<ProcessStatus> statuses);
}
