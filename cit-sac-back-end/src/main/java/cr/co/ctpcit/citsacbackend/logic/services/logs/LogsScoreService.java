package cr.co.ctpcit.citsacbackend.logic.services.logs;

import cr.co.ctpcit.citsacbackend.logic.dto.logs.EnglishExamLogDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LogsScoreService {
  List<EnglishExamLogDto> getLogsScoresByProcessId(int trackTestId);
}
