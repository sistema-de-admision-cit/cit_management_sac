package cr.co.ctpcit.citsacbackend.data.repositories.logs.englishExams;

import cr.co.ctpcit.citsacbackend.data.entities.logs.englishExams.LogsScoreEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface LogsScoreRepository extends JpaRepository<LogsScoreEntity, Integer> {
  List<LogsScoreEntity> getLogsScoreEntitiesByProcessId(@NotNull Integer processId);
}
