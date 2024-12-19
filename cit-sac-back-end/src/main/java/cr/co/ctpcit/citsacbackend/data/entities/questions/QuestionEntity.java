package cr.co.ctpcit.citsacbackend.data.entities.questions;

import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionLevel;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import cr.co.ctpcit.citsacbackend.data.enums.SelectionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tbl_questions")
public class QuestionEntity {
  @Id
  @Column(name = "question_id", columnDefinition = "INT UNSIGNED")
  private Long id;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "question_type", nullable = false, columnDefinition = "enum('ACA','DAI')")
  private QuestionType questionType;

  @Size(max = 512)
  @NotNull
  @Column(name = "question_text", nullable = false, length = 512)
  private String questionText;

  @Size(max = 255)
  @Column(name = "image_url")
  private String imageUrl;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "question_grade", nullable = false,
      columnDefinition = "enum('FIRST', 'SECOND', 'THIRD', 'FOURTH', 'FIFTH', 'SIXTH', 'SEVENTH', 'EIGHTH', 'NINTH', 'TENTH')")
  private Grades questionGrade;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "question_level", nullable = false,
      columnDefinition = "enum('EASY', 'MEDIUM', 'HARD')")
  private QuestionLevel questionLevel;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "selection_type", nullable = false,
      columnDefinition = "enum('UNIQUE', 'MULTIPLE')")
  private SelectionType selectionType;

  @NotNull
  @ColumnDefault("0")
  @Column(name = "deleted", nullable = false)
  private Boolean deleted = false;

  @OneToMany(mappedBy = "question")
  private List<QuestionOptionEntity> questionOptionEntities = new ArrayList<>();

}
