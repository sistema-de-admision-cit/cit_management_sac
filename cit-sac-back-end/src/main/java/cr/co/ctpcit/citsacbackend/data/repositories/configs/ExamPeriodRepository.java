package cr.co.ctpcit.citsacbackend.data.repositories.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.ExamPeriodEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ExamPeriodRepository extends JpaRepository<ExamPeriodEntity, Long> {
  @Query("SELECT ep FROM ExamPeriodEntity ep WHERE YEAR(ep.startDate) = :year")
  List<ExamPeriodEntity> findByYear(int year);

  boolean existsByStartDateAndEndDate(@NotNull LocalDate startDate, @NotNull LocalDate endDate);

  @Query(
      "SELECT ep FROM ExamPeriodEntity ep WHERE :startDate BETWEEN ep.startDate AND ep.endDate OR :endDate BETWEEN ep.startDate AND ep.endDate")
  List<ExamPeriodEntity> findByStartDateBetweenOrEndDateBetween(@NotNull LocalDate startDate,
      @NotNull LocalDate endDate);

  @Query("SELECT ep FROM ExamPeriodEntity ep WHERE :date BETWEEN ep.startDate AND ep.endDate")
  List<ExamPeriodEntity> findByDateBetween(@NotNull LocalDate date);
}
