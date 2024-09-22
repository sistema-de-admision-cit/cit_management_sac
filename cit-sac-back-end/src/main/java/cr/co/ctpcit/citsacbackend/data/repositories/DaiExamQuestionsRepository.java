package cr.co.ctpcit.citsacbackend.data.repositories;

import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiExamQuestionsEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiExamQuestionsEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DaiExamQuestionsRepository
    extends JpaRepository<DaiExamQuestionsEntity, DaiExamQuestionsEntityId> {
  @Query(
      "SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM DaiExamQuestionsEntity a WHERE a.id.questionId = :questionId")
  boolean existsByQuestionId(@Param("questionId") Integer questionId);

  List<DaiExamQuestionsEntity> findAllByIdExamId(Integer examId);
}
