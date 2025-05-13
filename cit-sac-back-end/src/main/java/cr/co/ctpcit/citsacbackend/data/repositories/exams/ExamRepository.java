package cr.co.ctpcit.citsacbackend.data.repositories.exams;

import cr.co.ctpcit.citsacbackend.data.entities.exams.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Repository interface for managing {@link ExamEntity} entities.
 * Provides a method to invoke a stored procedure that processes English exam results and logs the outcome.
 */
public interface ExamRepository extends JpaRepository<ExamEntity, Long> {

  /**
   * Calls the stored procedure {@code usp_Process_English_Exam_And_Log} to process an English exam result
   * and log the operation details.
   *
   * @param firstName   the first name of the student
   * @param lastNames   the last names of the student
   * @param examDate    the date the exam was taken, formatted as a string
   * @param trackTestId the ID of the TrackTest associated with the exam
   * @param newScore    the new score obtained by the student
   * @param level       the English level assigned to the student
   * @param processId   the ID of the associated process
   */

  @Modifying
  @Transactional
  @Procedure(name = "usp_Process_English_Exam_And_Log")
  void usp_process_english_exam_and_log(@Param("p_first_name") String firstName,
      @Param("p_last_names") String lastNames, @Param("p_exam_date") String examDate,
      @Param("p_tracktest_id") Long trackTestId, @Param("p_new_score") BigDecimal newScore,
      @Param("p_level") String level, @Param("p_process_id") Integer processId);

  /**
   * Counts the number of unique students who have taken at least one academic exam (type 'ACA').
   *
   * @return the number of distinct students with academic exams
   */

  @Query("SELECT COUNT(DISTINCT e.enrollment.student) " +
          "FROM ExamEntity e " +
          "WHERE e.examType = 'ACA'")
  Long countStudentsWithAcademicExams();

  /**
   * Counts the number of unique students who have taken at least one DAI exam (type 'DAI').
   *
   * @return the number of distinct students with DAI exams
   */
  
  @Query("SELECT COUNT(DISTINCT e.enrollment.student) " +
          "FROM ExamEntity e " +
          "WHERE e.examType = 'DAI'")
  Long countStudentsWithDAIExams();

  /**
   * Counts the number of unique students with academic exams (type 'ACA')
   * matching the given search value in their ID, first name, first surname,
   * second surname, or ID number.
   *
   * @param value the search keyword to match student attributes
   * @return the number of distinct students with academic exams matching the search
   */

  @Query("SELECT COUNT(DISTINCT e.enrollment.student) " +
          "FROM ExamEntity e " +
          "JOIN e.enrollment enr " +
          "JOIN enr.student s " +
          "JOIN s.studentPerson p " +
          "WHERE e.examType = 'ACA' " +
          "AND (CAST(s.id AS string) LIKE %:value% " +
          "OR LOWER(p.firstName) LIKE LOWER(CONCAT('%', :value, '%')) " +
          "OR LOWER(p.firstSurname) LIKE LOWER(CONCAT('%', :value, '%')) " +
          "OR LOWER(p.secondSurname) LIKE LOWER(CONCAT('%', :value, '%')) " +
          "OR p.idNumber LIKE %:value%)")
  Long countStudentsWithAcademicExamsBySearch(@Param("value") String value);

  /**
   * Counts the number of unique students with DAI exams (type 'DAI')
   * matching the given search value in their ID, first name, first surname,
   * second surname, or ID number.
   *
   * @param value the search keyword to match student attributes
   * @return the number of distinct students with DAI exams matching the search
   */

  @Query("SELECT COUNT(DISTINCT e.enrollment.student) " +
          "FROM ExamEntity e " +
          "JOIN e.enrollment enr " +
          "JOIN enr.student s " +
          "JOIN s.studentPerson p " +
          "WHERE e.examType = 'DAI' " +
          "AND (CAST(s.id AS string) LIKE %:value% " +
          "OR LOWER(p.firstName) LIKE LOWER(CONCAT('%', :value, '%')) " +
          "OR LOWER(p.firstSurname) LIKE LOWER(CONCAT('%', :value, '%')) " +
          "OR LOWER(p.secondSurname) LIKE LOWER(CONCAT('%', :value, '%')) " +
          "OR p.idNumber LIKE %:value%)")
  Long countStudentsWithDAIExamsBySearch(@Param("value") String value);
}
