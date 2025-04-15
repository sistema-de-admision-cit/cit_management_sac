package cr.co.ctpcit.citsacbackend.data.repositories.exams;

import cr.co.ctpcit.citsacbackend.data.entities.exams.EnglishExamEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * Repository interface for managing {@link EnglishExamEntity} entities.
 * Includes a method for invoking a stored procedure to process English exam data and log the result.
 */
@Repository
public interface EnglishExamRepository extends JpaRepository<EnglishExamEntity, Long> {

  /**
   * Calls the stored procedure {@code usp_process_english_exam_and_log} to process the result of an English exam
   * and log the corresponding data.
   *
   * @param firstName   the first name of the student
   * @param lastNames   the last names of the student
   * @param examDate    the date the exam was taken, formatted as a string
   * @param trackTestId the ID of the TrackTest record associated with the exam
   * @param newScore    the new score achieved by the student
   * @param level       the English level determined by the exam
   * @param processId   the ID of the associated process
   */

  @Modifying
  @Transactional
  @Procedure(name = "usp_process_english_exam_and_log")
  public void usp_process_english_exam_and_log(@Param("p_first_name") String firstName,
      @Param("p_last_names") String lastNames, @Param("p_exam_date") String examDate,
      @Param("p_tracktest_id") Long trackTestId, @Param("p_new_score") BigDecimal newScore,
      @Param("p_level") String level, @Param("p_process_id") Integer processId);
}
