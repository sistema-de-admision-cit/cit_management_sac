package cr.co.ctpcit.citsacbackend.data.repositories.exam.academic;

import cr.co.ctpcit.citsacbackend.data.entities.exams.academic.AcademicExamQuestionsEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.academic.AcademicExamQuestionsEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AcademicExamQuestionRepository
    extends JpaRepository<AcademicExamQuestionsEntity, AcademicExamQuestionsEntityId> {
  @Query(
      "SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM AcademicExamQuestionsEntity a WHERE a.id.questionId = :questionId")
  boolean existsByQuestionId(@Param("questionId") Integer questionId);

  List<AcademicExamQuestionsEntity> findAllByIdExamId(Integer examId);
}


