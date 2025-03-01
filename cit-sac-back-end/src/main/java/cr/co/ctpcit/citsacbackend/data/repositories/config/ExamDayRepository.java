package cr.co.ctpcit.citsacbackend.data.repositories.config;

import cr.co.ctpcit.citsacbackend.data.entities.config.ExamDayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamDayRepository extends JpaRepository<ExamDayEntity, Long> {
}
