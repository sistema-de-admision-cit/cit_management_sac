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
 * Data Transfer Object (DTO) representing a question in an exam.
 * Contains the question's details including type, text, image URL, grade, level, selection type,
 * whether the question is deleted, and the options associated with the question.
 */
@Builder
public record QuestionDto(Long id, @NotNull QuestionType questionType,
                          @NotNull @Size(max = 512) String questionText,
                          @Size(max = 255) String imageUrl, @NotNull Grades questionGrade,
                          @NotNull QuestionLevel questionLevel,
                          @NotNull SelectionType selectionType, @NotNull Boolean deleted,
                          List<QuestionOptionDto> questionOptions) implements Serializable {
}
