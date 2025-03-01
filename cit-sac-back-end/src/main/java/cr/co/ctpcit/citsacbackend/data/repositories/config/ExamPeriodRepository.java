package cr.co.ctpcit.citsacbackend.data.repositories.config;

import cr.co.ctpcit.citsacbackend.data.entities.config.ExamPeriodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamPeriodRepository extends JpaRepository<ExamPeriodEntity, Long> {
}
