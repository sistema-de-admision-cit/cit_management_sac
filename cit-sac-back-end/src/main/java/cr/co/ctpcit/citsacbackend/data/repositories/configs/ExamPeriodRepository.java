package cr.co.ctpcit.citsacbackend.data.repositories.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.ExamPeriodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamPeriodRepository extends JpaRepository<ExamPeriodEntity, Long> {
}
