package cr.co.ctpcit.citsacbackend.logic.dto.exams.english;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Represents the Data Transfer Object (DTO) for updating exam scores.
 * Contains a list of English score entries to be updated.
 */
@Getter
@Setter
public class ExamScoreUpdateRequestDTO {
  private List<EnglishScoreEntryDTO> scoreEntries;
}
