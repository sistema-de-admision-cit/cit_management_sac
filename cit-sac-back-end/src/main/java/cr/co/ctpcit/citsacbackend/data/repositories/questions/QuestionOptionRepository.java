package cr.co.ctpcit.citsacbackend.data.repositories.questions;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repository interface for managing {@link QuestionOptionEntity} entities.
 * Extends {@link JpaRepository} to provide standard CRUD operations and
 * {@link JpaSpecificationExecutor} to support dynamic query execution.
 */
public interface QuestionOptionRepository extends JpaRepository<QuestionOptionEntity, Long>,
    JpaSpecificationExecutor<QuestionOptionEntity> {
}
