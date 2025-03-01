package cr.co.ctpcit.citsacbackend.data.repositories.questions;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface QuestionRepository
    extends JpaRepository<QuestionEntity, Long>, JpaSpecificationExecutor<QuestionEntity> {
  /**
   * Get all questions in a paginated way.
   */
  @Override
  Page<QuestionEntity> findAll(Pageable pageable);

  /**
   * Soft delete a question.
   */
  @Modifying
  @Transactional
  @Query("UPDATE QuestionEntity q SET q.deleted = true WHERE q.id = :id")
  void softDeleteQuestion(Long id);

  /**
   * Find all questions by question text and return them in a paginated way.
   */
  Page<QuestionEntity> findAllByQuestionTextContaining(String questionText, Pageable pageable);
}
