package cr.co.ctpcit.citsacbackend.logic.dto.exams;

import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionLevel;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import cr.co.ctpcit.citsacbackend.data.enums.SelectionType;
import lombok.*;

@Builder
public record QuestionDaiDto(Long id, QuestionType questionType, String questionText,
                             String imageUrl, Grades questionGrade, QuestionLevel questionLevel,
                             SelectionType selectionType, boolean deleted, String response) {
}
