package cr.co.ctpcit.citsacbackend.logic.dto.exams;

import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import cr.co.ctpcit.citsacbackend.data.enums.SelectionType;
import lombok.*;

import java.util.List;

@Builder
public record QuestionAcaDto(Long id, QuestionType questionType, String questionText,
                             String imageUrl, SelectionType selectionType, boolean deleted,
                             List<QuestionOptionAcaDto> questionOptions) {
}
