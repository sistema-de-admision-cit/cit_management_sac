package cr.co.ctpcit.citsacbackend.data.repositories.exams;

import cr.co.ctpcit.citsacbackend.data.entities.exams.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface ExamRepository extends JpaRepository<ExamEntity, Long> {

  @Modifying
  @Transactional
  @Procedure(name = "usp_Process_English_Exam_And_Log")
  void usp_process_english_exam_and_log(@Param("p_first_name") String firstName,
      @Param("p_last_names") String lastNames, @Param("p_exam_date") String examDate,
      @Param("p_tracktest_id") Long trackTestId, @Param("p_new_score") BigDecimal newScore,
      @Param("p_level") String level, @Param("p_process_id") Integer processId);
}
