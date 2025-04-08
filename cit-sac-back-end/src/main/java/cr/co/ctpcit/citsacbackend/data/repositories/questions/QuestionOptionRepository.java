package cr.co.ctpcit.citsacbackend.data.repositories.questions;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuestionOptionRepository extends JpaRepository<QuestionOptionEntity, Long>,
    JpaSpecificationExecutor<QuestionOptionEntity> {
}
