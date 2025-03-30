package cr.co.ctpcit.citsacbackend.logic.services.exams.english;

import cr.co.ctpcit.citsacbackend.data.entities.exams.EnglishExamEntity;
import cr.co.ctpcit.citsacbackend.data.repositories.exams.EnglishExamRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.EnglishScoreEntryDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.logs.EnglishExamLogDto;
import cr.co.ctpcit.citsacbackend.logic.services.logs.LogsScoreServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.Instant;
import java.util.List;

@Service
public class EnglishExamServiceImpl implements EnglishExamService {
  private final EnglishExamRepository englishExamRepository;
  private final LogsScoreServiceImpl logsScoreService;

  @Autowired  // Constructor injection
  public EnglishExamServiceImpl(EnglishExamRepository englishExamRepository,
      LogsScoreServiceImpl logsScoreService) {
    this.englishExamRepository = englishExamRepository;
    this.logsScoreService = logsScoreService;
  }

  @Override
  public List<EnglishExamEntity> getAll() {
    return englishExamRepository.findAll();
  }

  @Override
  public EnglishExamEntity getById(Long id) {
    return englishExamRepository.findById(id).orElse(null);
  }

  @Override
  public EnglishExamEntity save(EnglishExamEntity englishExamEntity) {
    return englishExamRepository.save(englishExamEntity);
  }

  @Override
  public void delete(Long id) {
    englishExamRepository.deleteById(id);
  }

  @Override
  @Transactional
  public List<EnglishExamLogDto> processEnglishScores(List<EnglishScoreEntryDTO> englishScores) {
    Instant now = Instant.now(); // process id for the stored procedure (tbl_logsscore)
    Integer processId = now.getNano();

    for (EnglishScoreEntryDTO score : englishScores) {
      // Normalizar nombres y apellidos
      String normalizedNames = normalizeString(score.names());
      String normalizedLastNames = normalizeString(score.lastNames());

      // usp_process_english_exam_and_log
      // procedure to update the score of an english exam and log the change in the database
      englishExamRepository.usp_process_english_exam_and_log(normalizedNames, normalizedLastNames,
          score.lastTest(), score.id(),
          BigDecimal.valueOf(Double.parseDouble(score.core().replace("%", ""))), score.level(),
          processId);
    }

    return logsScoreService.getLogsScoresByProcessId(processId);
  }

  // normalize the string to lowercase and remove accents
  private String normalizeString(String input) {
    if (input == null)
      return null;
    String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
    return normalized.replaceAll("\\p{M}", "").toLowerCase();
  }
}
