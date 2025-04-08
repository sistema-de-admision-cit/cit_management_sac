package cr.co.ctpcit.citsacbackend.data.repositories.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.ExamDayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamDayRepository extends JpaRepository<ExamDayEntity, Long> {
}
