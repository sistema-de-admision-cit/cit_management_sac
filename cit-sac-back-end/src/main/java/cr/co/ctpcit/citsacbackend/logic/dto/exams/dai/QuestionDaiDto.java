package cr.co.ctpcit.citsacbackend.logic.dto.exams.dai;

import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import cr.co.ctpcit.citsacbackend.data.enums.SelectionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Data transfer object for the questions of the DAI exam
 * @param id
 * @param questionType
 * @param questionText
 * @param imageUrl
 * @param selectionType
 * @param deleted
 * @param response
 */
@Builder
public record QuestionDaiDto(Long id, @NotNull QuestionType questionType, String questionText,
                             String imageUrl, @NotNull SelectionType selectionType, boolean deleted,
                             @Size(max = 512) String response) {
}
