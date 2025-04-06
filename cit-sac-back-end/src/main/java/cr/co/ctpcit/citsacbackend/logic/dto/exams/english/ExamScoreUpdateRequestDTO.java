package cr.co.ctpcit.citsacbackend.logic.dto.exams.english;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExamScoreUpdateRequestDTO {
  private List<EnglishScoreEntryDTO> scoreEntries;
}
