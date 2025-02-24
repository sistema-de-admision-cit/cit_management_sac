package cr.co.ctpcit.citsacbackend.data.repositories.questions;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionLevel;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
  /**
   * Get all questions in a paginated way.
   */
  @Override
  Page<QuestionEntity> findAll(Pageable pageable);


  /**
   * Find all questions by question type.
   *
   * @param questionType
   * @return List of questions.
   * @see QuestionType
   */
  Page<QuestionEntity> findAllByQuestionType(QuestionType questionType, Pageable pageable);

  /**
   * Find all questions by question grade.
   *
   * @param questionGrade
   * @return List of questions.
   * @see Grades
   */
  List<QuestionEntity> findAllByQuestionGrade(Grades questionGrade);

  /**
   * Find all questions by question level.
   *
   * @param questionLevel
   * @return List of questions.
   * @see QuestionLevel
   */
  List<QuestionEntity> findAllByQuestionLevel(QuestionLevel questionLevel);

  List<QuestionEntity> findAllByDeletedFalse();

  List<QuestionEntity> findAllByDeletedTrue();

}
