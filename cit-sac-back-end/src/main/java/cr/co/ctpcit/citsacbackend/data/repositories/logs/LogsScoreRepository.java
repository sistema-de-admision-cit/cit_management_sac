package cr.co.ctpcit.citsacbackend.data.repositories.logs;

import cr.co.ctpcit.citsacbackend.data.entities.logs.LogsScoreEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogsScoreRepository extends JpaRepository<LogsScoreEntity, Integer> {

  List<LogsScoreEntity> getLogsScoreEntitiesByProcessId(@NotNull Integer processId);
}
