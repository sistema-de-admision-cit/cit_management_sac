package cr.co.ctpcit.citsacbackend.data.repositories.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.ExamDayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link ExamDayEntity} persistence operations.
 * This interface extends {@link JpaRepository} to provide standard CRUD operations
 * and query methods for the {@code ExamDayEntity}. It uses {@code Long} as the ID type.
 */
public interface ExamDayRepository extends JpaRepository<ExamDayEntity, Long> {
}
