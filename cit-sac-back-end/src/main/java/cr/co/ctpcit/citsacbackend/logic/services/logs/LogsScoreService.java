package cr.co.ctpcit.citsacbackend.logic.services.logs;

import cr.co.ctpcit.citsacbackend.logic.dto.logs.EnglishExamLogDto;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * Service interface for retrieving English exam score logs.
 * Provides methods to access historical score data for English exam processes.
 */
@Service
public interface LogsScoreService {
  /**
   * Retrieves all score logs associated with a specific English exam process.
   *
   * @param trackTestId the unique identifier of the exam process/tracking test
   * @return a list of {@link EnglishExamLogDto} containing score log information
   *         for the specified process, or an empty list if none found
   */
  List<EnglishExamLogDto> getLogsScoresByProcessId(int trackTestId);
}
