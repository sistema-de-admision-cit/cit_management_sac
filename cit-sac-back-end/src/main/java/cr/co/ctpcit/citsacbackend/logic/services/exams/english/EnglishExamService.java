package cr.co.ctpcit.citsacbackend.logic.services.exams.english;


import cr.co.ctpcit.citsacbackend.data.entities.exams.EnglishExamEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.EnglishScoreEntryDTO;
import cr.co.ctpcit.citsacbackend.logic.dto.logs.EnglishExamLogDto;

import java.util.List;
/**
 * Service interface for managing English exam operations.
 * Provides methods for CRUD operations and processing English exam scores.
 */
public interface EnglishExamService {
  /**
   * Retrieves all English exam records.
   *
   * @return List of all English exam entities
   */
  List<EnglishExamEntity> getAll();
  /**
   * Retrieves an English exam record by its ID.
   *
   * @param id the ID of the English exam to retrieve
   * @return the English exam entity with the specified ID
   */
  EnglishExamEntity getById(Long id);
  /**
   * Saves an English exam record.
   *
   * @param englishExamEntity the English exam entity to save
   * @return the saved English exam entity
   */
  EnglishExamEntity save(EnglishExamEntity englishExamEntity);
  /**
   * Deletes an English exam record by its ID.
   *
   * @param id the ID of the English exam to delete
   */
  void delete(Long id);
  /**
   * Processes a batch of English exam scores.
   *
   * @param englishScores list of English score entries to process
   * @return list of processing logs containing results for each score entry
   */
  List<EnglishExamLogDto> processEnglishScores(List<EnglishScoreEntryDTO> englishScores);
}
