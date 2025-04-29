package cr.co.ctpcit.citsacbackend.data.repositories.exams;

import cr.co.ctpcit.citsacbackend.data.entities.exams.EnglishExamEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface EnglishExamRepository extends JpaRepository<EnglishExamEntity, Long> {
  @Modifying
  @Transactional
  @Procedure(name = "usp_process_english_exam_and_log")
  void usp_process_english_exam_and_log(@Param("p_first_name") String firstName,
      @Param("p_last_names") String lastNames, @Param("p_exam_date") String examDate,
      @Param("p_tracktest_id") Long trackTestId, @Param("p_new_score") BigDecimal newScore,
      @Param("p_level") String level, @Param("p_process_id") Integer processId);
}
