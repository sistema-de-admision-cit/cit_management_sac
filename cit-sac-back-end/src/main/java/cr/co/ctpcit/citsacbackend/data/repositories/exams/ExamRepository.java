package cr.co.ctpcit.citsacbackend.data.repositories.exams;

import cr.co.ctpcit.citsacbackend.data.entities.exams.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<ExamEntity, Long> {
}
