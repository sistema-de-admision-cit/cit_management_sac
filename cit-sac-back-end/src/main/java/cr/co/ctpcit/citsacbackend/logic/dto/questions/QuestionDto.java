package cr.co.ctpcit.citsacbackend.logic.dto.questions;

import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionLevel;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import cr.co.ctpcit.citsacbackend.data.enums.SelectionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity}
 */
@Builder
public record QuestionDto(Long id, @NotNull QuestionType questionType,
                          @NotNull @Size(max = 512) String questionText,
                          @Size(max = 255) String imageUrl, @NotNull Grades questionGrade,
                          @NotNull QuestionLevel questionLevel,
                          @NotNull SelectionType selectionType, @NotNull Boolean deleted,
                          List<QuestionOptionDto> questionOptions) implements Serializable {
}
