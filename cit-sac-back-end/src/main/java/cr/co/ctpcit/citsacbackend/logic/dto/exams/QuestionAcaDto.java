package cr.co.ctpcit.citsacbackend.logic.dto.exams;

import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionLevel;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import cr.co.ctpcit.citsacbackend.data.enums.SelectionType;
import lombok.*;

import java.util.List;

@Builder
public record QuestionAcaDto(Long id, QuestionType questionType, String questionText,
                             String imageUrl, Grades questionGrade, QuestionLevel questionLevel,
                             SelectionType selectionType, boolean deleted,
                             List<QuestionOptionAcaDto> questionOptions) {
}
