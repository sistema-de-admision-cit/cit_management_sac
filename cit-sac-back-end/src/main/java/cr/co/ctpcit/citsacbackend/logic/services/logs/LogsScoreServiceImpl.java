package cr.co.ctpcit.citsacbackend.logic.services.logs;

import cr.co.ctpcit.citsacbackend.data.entities.logs.LogsScoreEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.logs.LogsScoreRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.logs.EnglishExamLogDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * Implementation of {@link LogsScoreService} for managing English exam score logs.
 * This service handles retrieval of historical score data for English exam processes.
 */
@Service
public class LogsScoreServiceImpl implements LogsScoreService {
  private final LogsScoreRepository logsScoreRepository;
  /**
   * Constructs a new LogsScoreServiceImpl with the required repository.
   *
   * @param logsScoreRepository repository for accessing score log data
   */
  public LogsScoreServiceImpl(LogsScoreRepository logsScoreRepository) {
    this.logsScoreRepository = logsScoreRepository;
  }
  /**
   * Retrieves all score logs associated with a specific English exam process.
   * Converts the entity data to DTO format for the API response.
   *
   * @param processId the unique identifier of the exam process
   * @return list of English exam log DTOs containing score history information,
   *         or empty list if no logs found for the given process ID
   */
  @Override
  public List<EnglishExamLogDto> getLogsScoresByProcessId(int processId) {
    List<LogsScoreEntity> logsScoreEntities =
        logsScoreRepository.getLogsScoreEntitiesByProcessId(processId);

    System.out.println("logsScoreEntities: " + logsScoreEntities);

    List<EnglishExamLogDto> logs = new ArrayList<>();

    for (LogsScoreEntity logsScoreEntity : logsScoreEntities) {
      logs.add(
          new EnglishExamLogDto(logsScoreEntity.getProcessId(), logsScoreEntity.getEnrollmentId(),
              logsScoreEntity.getTracktestId(), logsScoreEntity.getPreviousScore(),
              logsScoreEntity.getNewScore(), logsScoreEntity.getExamDate(),
              logsScoreEntity.getStatus(), logsScoreEntity.getErrorMessage()));
    }

    return logs;
  }
}
