package cr.co.ctpcit.citsacbackend.logic.dto.exams;

import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionLevel;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import cr.co.ctpcit.citsacbackend.data.enums.SelectionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDaiDto extends Question {
  private String response;

  public QuestionDaiDto(Long id, QuestionType questionType, String questionText, String imageUrl,
      Grades questionGrade, QuestionLevel questionLevel, SelectionType selectionType,
      boolean deleted, String response) {
    super(id, questionType, questionText, imageUrl, questionGrade, questionLevel, selectionType,
        deleted);
    this.response = response;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    if (!super.equals(o))
      return false;
    QuestionDaiDto that = (QuestionDaiDto) o;
    return response.equals(that.response);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
