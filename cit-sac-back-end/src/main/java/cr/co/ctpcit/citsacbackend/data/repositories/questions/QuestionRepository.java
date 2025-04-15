package cr.co.ctpcit.citsacbackend.data.repositories.questions;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository interface for managing {@link QuestionEntity} entities.
 * Extends {@link JpaRepository} for standard CRUD operations and {@link JpaSpecificationExecutor} for dynamic queries.
 */
public interface QuestionRepository
    extends JpaRepository<QuestionEntity, Long>, JpaSpecificationExecutor<QuestionEntity> {

  /**
   * Retrieves all questions in a paginated manner.
   *
   * @param pageable the pagination information
   * @return a page of {@link QuestionEntity} objects
   */
  @Override
  Page<QuestionEntity> findAll(Pageable pageable);

  /**
   * Soft deletes a question by marking it as deleted without actually removing it from the database.
   *
   * @param id the ID of the question to be soft deleted
   */
  @Modifying
  @Transactional
  @Query("UPDATE QuestionEntity q SET q.deleted = true WHERE q.id = :id")
  void softDeleteQuestion(Long id);

  /**
   * Finds all questions containing the specified text in their question text.
   * The results are returned in a paginated way.
   *
   * @param questionText the text to search for in the question
   * @param pageable the pagination information
   * @return a page of {@link QuestionEntity} objects that contain the specified text
   */
  Page<QuestionEntity> findAllByQuestionTextContaining(String questionText, Pageable pageable);

  /**
   * Finds a random selection of questions based on the grade and type.
   * The questions returned are not marked as deleted.
   *
   * @param grade the grade to filter the questions by
   * @param type the type of question to filter by
   * @param quantity the number of random questions to retrieve
   * @return a list of randomly selected {@link QuestionEntity} objects
   */
  @Query(
      "SELECT q FROM QuestionEntity q WHERE q.deleted = false AND q.questionGrade = :grade AND q.questionType = :type ORDER BY RAND() LIMIT :quantity")
  List<QuestionEntity> findRandomQuestionsByGradeAndType(Grades grade, QuestionType type,
      int quantity);

  /**
   * Finds a specified number of questions by type, excluding those marked as deleted.
   *
   * @param type the type of question to filter by
   * @param quantity the number of questions to retrieve
   * @return a list of {@link QuestionEntity} objects of the specified type
   */
  @Query(
      "SELECT q FROM QuestionEntity q WHERE q.deleted = false AND q.questionType = :type ORDER BY q.id LIMIT :quantity")
  List<QuestionEntity> findQuestionsByType(QuestionType type, int quantity);
}
