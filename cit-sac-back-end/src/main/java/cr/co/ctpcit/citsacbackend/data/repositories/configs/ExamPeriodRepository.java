package cr.co.ctpcit.citsacbackend.data.repositories.configs;

import cr.co.ctpcit.citsacbackend.data.entities.configs.ExamPeriodEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for managing {@link ExamPeriodEntity} entities.
 * Provides custom query methods to retrieve and validate exam periods based on dates.
 */
public interface ExamPeriodRepository extends JpaRepository<ExamPeriodEntity, Long> {

  /**
   * Finds all exam periods that start within the given year.
   *
   * @param year the year to filter exam periods by
   * @return a list of exam periods starting in the specified year
   */
  @Query("SELECT ep FROM ExamPeriodEntity ep WHERE YEAR(ep.startDate) = :year")
  List<ExamPeriodEntity> findByYear(int year);

  /**
   * Checks if an exam period exists with the given start and end dates.
   *
   * @param startDate the start date to check
   * @param endDate the end date to check
   * @return true if an exam period exists with the specified dates, false otherwise
   */
  boolean existsByStartDateAndEndDate(@NotNull LocalDate startDate, @NotNull LocalDate endDate);

  /**
   * Finds exam periods where either the given start or end date falls within an existing period.
   *
   * @param startDate the start date to check
   * @param endDate the end date to check
   * @return a list of exam periods that overlap with the given dates
   */
  @Query(
      "SELECT ep FROM ExamPeriodEntity ep WHERE :startDate BETWEEN ep.startDate AND ep.endDate OR :endDate BETWEEN ep.startDate AND ep.endDate")
  List<ExamPeriodEntity> findByStartDateBetweenOrEndDateBetween(@NotNull LocalDate startDate,
      @NotNull LocalDate endDate);

  /**
   * Finds exam periods where the given date falls between the start and end dates.
   *
   * @param date the date to check
   * @return a list of exam periods that include the specified date
   */
  @Query("SELECT ep FROM ExamPeriodEntity ep WHERE :date BETWEEN ep.startDate AND ep.endDate")
  List<ExamPeriodEntity> findByDateBetween(@NotNull LocalDate date);
}
