package cr.co.ctpcit.citsacbackend.data.repositories.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.ExamPeriodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExamPeriodRepository extends JpaRepository<ExamPeriodEntity, Long> {
  @Query("SELECT ep FROM ExamPeriodEntity ep WHERE YEAR(ep.startDate) = :year")
  List<ExamPeriodEntity> findByYear(int year);
}
