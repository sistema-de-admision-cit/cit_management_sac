package cr.co.ctpcit.citsacbackend.data.entities.questions;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_question_options")
public class  QuestionOptionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "option_id", columnDefinition = "INT UNSIGNED")
  private Long id;

  @NotNull
  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
  @JoinColumn(name = "question_id", nullable = false, columnDefinition = "INT UNSIGNED")
  private QuestionEntity question;

  @NotNull
  @ColumnDefault("0")
  @Builder.Default
  @Column(name = "is_correct", nullable = false)
  private Boolean isCorrect = false;

  @Size(max = 255)
  @NotNull
  @Column(name = "`option`", nullable = false)
  private String option;

}
