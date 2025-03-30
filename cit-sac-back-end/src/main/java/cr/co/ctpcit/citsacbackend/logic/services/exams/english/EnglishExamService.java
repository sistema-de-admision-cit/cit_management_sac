package cr.co.ctpcit.citsacbackend.logic.services.exams.english;


import cr.co.ctpcit.citsacbackend.data.entities.exams.EnglishExamEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.EnglishScoreEntryDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.logs.EnglishExamLogDto;

import java.util.List;

public interface EnglishExamService {
  List<EnglishExamEntity> getAll();

  EnglishExamEntity getById(Long id);

  EnglishExamEntity save(EnglishExamEntity englishExamEntity);

  void delete(Long id);

  List<EnglishExamLogDto> processEnglishScores(List<EnglishScoreEntryDTO> englishScores);
}
