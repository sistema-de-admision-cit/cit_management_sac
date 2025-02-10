package cr.co.ctpcit.citsacbackend.data.repositories.questions;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
}
