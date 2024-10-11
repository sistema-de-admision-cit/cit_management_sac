package cr.co.ctpcit.citsacbackend.logic.services.logs.englishExams;

import cr.co.ctpcit.citsacbackend.logic.dto.logs.englishExams.EnglishExamLogDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LogsScoreService {
  List<EnglishExamLogDto> getLogsScoresByProcessId(int trackTestId);
}
