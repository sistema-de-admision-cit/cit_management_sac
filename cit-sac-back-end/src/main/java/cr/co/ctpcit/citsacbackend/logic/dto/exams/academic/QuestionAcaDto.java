package cr.co.ctpcit.citsacbackend.logic.dto.exams.academic;

import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import cr.co.ctpcit.citsacbackend.data.enums.SelectionType;
import lombok.*;

import java.util.List;

/**
 * Represents the Data Transfer Object (DTO) for a question in an academic exam.
 * Contains information about the question, its type, options, and associated properties.
 */
@Builder
public record QuestionAcaDto(Long id, QuestionType questionType, String questionText,
                             String imageUrl, SelectionType selectionType, boolean deleted,
                             List<QuestionOptionAcaDto> questionOptions) {
}
