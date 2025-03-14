package cr.co.ctpcit.citsacbackend.logic.dto.exams;

import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionLevel;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import cr.co.ctpcit.citsacbackend.data.enums.SelectionType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class QuestionAcaDto extends Question {
  List<QuestionOptionAcaDto> questionOptions;

  public QuestionAcaDto(Long id, QuestionType questionType, String questionText, String imageUrl,
      Grades questionGrade, QuestionLevel questionLevel, SelectionType selectionType,
      boolean deleted, List<QuestionOptionAcaDto> questionOptions) {
    super(id, questionType, questionText, imageUrl, questionGrade, questionLevel, selectionType,
        deleted);
    this.questionOptions = questionOptions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    if (!super.equals(o))
      return false;
    QuestionAcaDto that = (QuestionAcaDto) o;
    return questionOptions.equals(that.questionOptions);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
