package cr.co.ctpcit.citsacbackend.data.repositories.logs;

import cr.co.ctpcit.citsacbackend.data.entities.logs.LogsScoreEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing {@link LogsScoreEntity} entities.
 * Provides custom query methods for interacting with logs related to score processes.
 */
@Repository
public interface LogsScoreRepository extends JpaRepository<LogsScoreEntity, Integer> {

  /**
   * Retrieves a list of {@link LogsScoreEntity} by the given process ID.
   *
   * @param processId the ID of the process to filter the logs by
   * @return a list of {@link LogsScoreEntity} associated with the provided process ID
   */
  List<LogsScoreEntity> getLogsScoreEntitiesByProcessId(@NotNull Integer processId);
}
