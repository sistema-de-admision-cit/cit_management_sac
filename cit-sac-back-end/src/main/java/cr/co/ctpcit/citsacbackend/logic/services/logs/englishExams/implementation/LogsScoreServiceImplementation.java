package cr.co.ctpcit.citsacbackend.logic.services.logs.englishExams.implementation;

import cr.co.ctpcit.citsacbackend.data.entities.logs.englishExams.LogsScoreEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.logs.englishExams.LogsScoreRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.logs.englishExams.EnglishExamLogDto;
import cr.co.ctpcit.citsacbackend.logic.services.logs.englishExams.LogsScoreService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogsScoreServiceImplementation implements LogsScoreService {
  private final LogsScoreRepository logsScoreRepository;

  public LogsScoreServiceImplementation(LogsScoreRepository logsScoreRepository) {
    this.logsScoreRepository = logsScoreRepository;
  }


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
              logsScoreEntity.getStatus().toString(), logsScoreEntity.getErrorMessage()));
    }

    return logs;
  }
}
