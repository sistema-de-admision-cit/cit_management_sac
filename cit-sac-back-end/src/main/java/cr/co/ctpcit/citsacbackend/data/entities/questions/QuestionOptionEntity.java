package cr.co.ctpcit.citsacbackend.data.entities.questions;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "tbl_question_options")
public class QuestionOptionEntity {
  @EmbeddedId
  private QuestionOptionEntityId id;

  @MapsId("questionId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "question_id", nullable = false, columnDefinition = "INT UNSIGNED")
  @JsonBackReference
  private QuestionEntity question;

  @NotNull
  @ColumnDefault("0")
  @Column(name = "is_correct", nullable = false)
  private Boolean isCorrect = false;

  @Size(max = 255)
  @NotNull
  @Column(name = "`option`", nullable = false)
  private String option;

}
