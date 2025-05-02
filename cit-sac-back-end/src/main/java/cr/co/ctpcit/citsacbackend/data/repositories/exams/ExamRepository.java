package cr.co.ctpcit.citsacbackend.data.repositories.exams;

import cr.co.ctpcit.citsacbackend.data.entities.exams.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
}
