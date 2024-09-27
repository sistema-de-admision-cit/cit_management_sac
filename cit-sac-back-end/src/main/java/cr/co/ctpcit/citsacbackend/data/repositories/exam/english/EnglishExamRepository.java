package cr.co.ctpcit.citsacbackend.data.repositories.exam.english;

import cr.co.ctpcit.citsacbackend.data.entities.exams.english.EnglishExamEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface EnglishExamRepository extends JpaRepository<EnglishExamEntity, Long> {

  @Procedure(name = "usp_process_english_exam")
  public void usp_process_english_exam(@Param("p_enrollment_id") Long enrollmentId,
      @Param("p_tracktest_id") Long trackTestId, @Param("p_new_score") BigDecimal newScore,
      @Param("p_exam_date") LocalDate examDate, @Param("p_level") String level,
      @Param("p_process_id") Integer processId);
}
