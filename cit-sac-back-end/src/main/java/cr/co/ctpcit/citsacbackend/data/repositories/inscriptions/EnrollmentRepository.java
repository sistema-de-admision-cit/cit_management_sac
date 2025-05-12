package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.KnownThrough;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import java.time.Instant;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link EnrollmentEntity} entities.
 * Provides custom query methods for various enrollment-related operations.
 */
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long>, JpaSpecificationExecutor<EnrollmentEntity> {

  /**
   * Retrieves all enrollments with statuses that are in process, including 'PENDING', 'ELIGIBLE', or 'INELIGIBLE'.
   * Supports pagination.
   *
   * @param pageable the pagination information
   * @return a {@link Page} containing the matching {@link EnrollmentEntity} entries
   */
  @Query(
      "SELECT e FROM EnrollmentEntity e WHERE e.status IN ('PENDING','ELIGIBLE','INELIGIBLE')")
  Page<EnrollmentEntity> findAllEnrollmentsInProcess(Pageable pageable);

  /**
   * Retrieves all enrollments associated with a specific student.
   *
   * @param student the student entity to filter enrollments
   * @return a list of {@link EnrollmentEntity} associated with the given student
   */
  List<EnrollmentEntity> findAllByStudent(@NotNull StudentEntity student);

  /**
   * Retrieves all enrollments in process for a specific student. Only includes statuses 'PENDING', 'ELIGIBLE', or 'INELIGIBLE'.
   *
   * @param student the student entity to filter enrollments
   * @return a list of {@link EnrollmentEntity} entries for the given student that are in process
   */
  @Query(
      "SELECT e FROM EnrollmentEntity e WHERE e.student = :student AND e.status IN ('PENDING','ELIGIBLE','INELIGIBLE')")
  List<EnrollmentEntity> findAllByStudentPerson_IdNumber_ThatAreInProcess(
      @NotNull StudentEntity student);

  /**
   * Retrieves all enrollments for a student identified by their student ID number.
   *
   * @param idNumber the ID number of the student
   * @return a list of {@link EnrollmentEntity} entries for the student
   */
  List<EnrollmentEntity> findAllByStudent_StudentPerson_IdNumber(@NotNull String idNumber);


  /**
   * Retrieves all enrollments for a list of students that have a status of 'PENDING', 'ELIGIBLE', or 'INELIGIBLE'.
   *
   * @param students a list of student entities to filter enrollments
   * @return a list of {@link EnrollmentEntity} entries for students in the list that are in process
   */
  @Query(
      "SELECT e FROM EnrollmentEntity e WHERE e.student IN :students AND e.status IN ('PENDING','ELIGIBLE','INELIGIBLE')")
  List<EnrollmentEntity> findAllByStudentInTheListThatHasEnrollmentsInProcess(
      List<StudentEntity> students, Pageable pageable);

  /**
   * Updates the exam date for a specific enrollment.
   *
   * @param id       the ID of the enrollment to update
   * @param examDate the new exam date to set
   */
  @Modifying
  @Transactional
  @Query("UPDATE EnrollmentEntity e SET e.examDate = :examDate WHERE e.id = :id")
  void updateEnrollmentExamDate(Long id, LocalDate examDate);

  /**
   * Updates the status of a specific enrollment.
   *
   * @param id     the ID of the enrollment to update
   * @param status the new status to set
   */
  @Modifying
  @Transactional
  @Query("UPDATE EnrollmentEntity e SET e.status = :status WHERE e.id = :id")
  void updateEnrollmentStatus(Long id, ProcessStatus status);

  /**
   * Updates the WhatsApp notification permission for a specific enrollment.
   *
   * @param id                the ID of the enrollment to update
   * @param whatsappPermission the new WhatsApp notification permission to set
   */
  @Modifying
  @Transactional
  @Query(
      "UPDATE EnrollmentEntity e SET e.whatsappNotification = :whatsappPermission WHERE e.id = :id")
  void updateEnrollmentWhatsappPermission(Long id, Boolean whatsappPermission);

  /**
   * Calls the stored procedure {@code usp_update_enrollment_and_log} to update the enrollment status and log the changes.
   *
   * @param enrollmentId          the ID of the enrollment to update
   * @param newStatus            the new status to assign to the enrollment
   * @param newExamDate          the new exam date to set
   * @param newWhatsappPermission the new WhatsApp notification permission
   * @param newPreviousGrades    the new previous grades value
   * @param comment              additional comments for the update
   * @param changedBy            the email of the user who made the changes
   */

  @Procedure(name = "usp_update_enrollment_and_log")
  void usp_update_enrollment_and_log(
          @Param("p_enrollment_id") Long enrollmentId,
          @Param("p_new_status") String newStatus,
          @Param("p_new_exam_date") Date newExamDate,
          @Param("p_new_whatsapp_permission") Boolean newWhatsappPermission,
          @Param("p_new_previous_grades") BigDecimal newPreviousGrades,
          @Param("p_comment") String comment,
          @Param("p_changed_by") String changedBy
  );

  @Query(
      "SELECT COUNT(DISTINCT(e.student)) FROM EnrollmentEntity e WHERE e.status IN ('PENDING','ELIGIBLE','INELIGIBLE')")
  Long countEnrollmentsInProcess();

  /**
   * Repository query method to find enrollments by student's ID number and status, with pagination support.
   * The status list can contain multiple process statuses.
   *
   * @param idNumber The ID number of the student to search for.
   * @param statuses A list of statuses to filter the enrollments by.
   * @param pageable The pagination information.
   * @return A page of enrollment entities that match the search criteria.
   */

  @Query("SELECT e FROM EnrollmentEntity e WHERE e.student.studentPerson.idNumber LIKE %:idNumber% AND e.status IN :statuses")
  Page<EnrollmentEntity> findByIdNumberAndStatusIn(
          @Param("idNumber") String idNumber,
          @Param("statuses") List<ProcessStatus> statuses,
          Pageable pageable
  );


  /**
   * Repository query method to find enrollments for a list of students with a specified status, with pagination support.
   *
   * @param students A list of students to filter enrollments by.
   * @param statuses A list of statuses to filter the enrollments by.
   * @param pageable The pagination information.
   * @return A page of enrollment entities that match the search criteria.
   */

  @Query("SELECT e FROM EnrollmentEntity e WHERE e.student IN :students AND e.status IN :statuses")
  Page<EnrollmentEntity> findAllByStudentsWithStatusIn(
          @Param("students") List<StudentEntity> students,
          @Param("statuses") List<ProcessStatus> statuses,
          Pageable pageable
  );


  /**
   * Repository query method to find an enrollment by student's ID number, without filtering by status.
   *
   * @param idNumber The ID number of the student to search for.
   * @return An optional enrollment entity that matches the search criteria.
   */

  @Query("SELECT e FROM EnrollmentEntity e WHERE e.student.studentPerson.idNumber = :idNumber")
  Optional<EnrollmentEntity> findByStudentStudentPersonIdNumber(@Param("idNumber") String idNumber);

  /**
   * Repository query method to find an enrollment by student's ID number and a list of possible statuses.
   *
   * @param idNumber The ID number of the student to search for.
   * @param statuses A list of statuses to filter the enrollment by.
   * @return An optional enrollment entity that matches the search criteria.
   */

  @Query("SELECT e FROM EnrollmentEntity e WHERE e.student.studentPerson.idNumber = :idNumber AND e.status IN :statuses")
  Optional<EnrollmentEntity> findByStudentStudentPersonIdNumberAndStatusIn(
          @Param("idNumber") String idNumber,
          @Param("statuses") List<ProcessStatus> statuses);

  /**
   * Counts the number of unique students whose enrollment status is either 'ELIGIBLE', 'ACCEPTED', or 'REJECTED',
   * and who have completed all three required exams: academic (ACA), DAI, and English (ENG).
   *
   * @return the number of students with complete exam records
   */

  @Query("SELECT COUNT(DISTINCT e.student) " +
          "FROM EnrollmentEntity e " +
          "WHERE e.status IN ('ELIGIBLE', 'ACCEPTED', 'REJECTED') " +
          "AND EXISTS (SELECT 1 FROM e.exams ex1 WHERE ex1.examType = 'ACA') " +
          "AND EXISTS (SELECT 1 FROM e.exams ex2 WHERE ex2.examType = 'DAI') " +
          "AND EXISTS (SELECT 1 FROM e.exams ex3 WHERE ex3.examType = 'ENG')")
  Long countStudentsWithCompleteExams();


  /**
   * Counts the number of unique students matching the given search term, whose enrollment status is either
   * 'ELIGIBLE', 'ACCEPTED', or 'REJECTED', and who have completed all three required exams: academic (ACA),
   * DAI, and English (ENG).
   *
   * @param searchTerm a string to match against the student's ID number, first name, first surname, or second surname
   * @return the number of students with complete exam records matching the search criteria
   */

  @Query("SELECT COUNT(DISTINCT e.student) " +
          "FROM EnrollmentEntity e " +
          "JOIN e.student s " +
          "JOIN s.studentPerson p " +
          "WHERE e.status IN ('ELIGIBLE', 'ACCEPTED', 'REJECTED') " +
          "AND EXISTS (SELECT 1 FROM e.exams ex1 WHERE ex1.examType = 'ACA') " +
          "AND EXISTS (SELECT 1 FROM e.exams ex2 WHERE ex2.examType = 'DAI') " +
          "AND EXISTS (SELECT 1 FROM e.exams ex3 WHERE ex3.examType = 'ENG') " +
          "AND (p.idNumber LIKE %:searchTerm% " +
          "OR LOWER(p.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
          "OR LOWER(p.firstSurname) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
          "OR LOWER(p.secondSurname) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
  Long countStudentsWithCompleteExamsBySearch(@Param("searchTerm") String searchTerm);


  /**
   * Retrieves a paginated list of enrollments that have a status of ELIGIBLE, ACCEPTED, or REJECTED,
   * and are associated with exams of all three required types: ACA, DAI, and ENG.
   *
   * @param pageable pagination information
   * @return a page of EnrollmentEntity instances matching the criteria
   */

  @Query("SELECT DISTINCT e " +
          "FROM EnrollmentEntity e " +
          "WHERE e.status IN ('ELIGIBLE', 'ACCEPTED', 'REJECTED') " +
          "AND EXISTS (SELECT 1 FROM e.exams ex1 WHERE ex1.examType = 'ACA') " +
          "AND EXISTS (SELECT 1 FROM e.exams ex2 WHERE ex2.examType = 'DAI') " +
          "AND EXISTS (SELECT 1 FROM e.exams ex3 WHERE ex3.examType = 'ENG')")
  Page<EnrollmentEntity> findAllWithCompleteExams(Pageable pageable);


  /**
   * Retrieves the most recent enrollment for a student with the specified ID number and a matching status,
   * ordered by enrollment date in descending order.
   *
   * @param idNumber the ID number of the student's person
   * @param statuses the list of enrollment statuses to filter by
   * @return an Optional containing the most recent matching EnrollmentEntity, or empty if none found
   */

  Optional<EnrollmentEntity> findTopByStudentStudentPersonIdNumberAndStatusInOrderByEnrollmentDateDesc(
          String idNumber,
          List<ProcessStatus> statuses);


}
