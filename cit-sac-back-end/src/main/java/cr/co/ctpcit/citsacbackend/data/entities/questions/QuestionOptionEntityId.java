package cr.co.ctpcit.citsacbackend.data.entities.questions;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class QuestionOptionEntityId implements Serializable {

  @Column(name = "option_id", columnDefinition = "INT UNSIGNED")
  private Long optionId;

  @Column(name = "question_id", columnDefinition = "INT UNSIGNED")
  private Long questionId;

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
      return false;
    QuestionOptionEntityId entity = (QuestionOptionEntityId) o;
    return Objects.equals(this.questionId, entity.questionId) && Objects.equals(this.optionId,
        entity.optionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(questionId, optionId);
  }

}
