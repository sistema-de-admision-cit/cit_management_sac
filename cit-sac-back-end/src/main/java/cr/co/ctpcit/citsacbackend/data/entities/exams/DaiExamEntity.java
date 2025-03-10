package cr.co.ctpcit.citsacbackend.data.entities.exams;

import cr.co.ctpcit.citsacbackend.data.enums.Recommendation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_dai_exams")
public class DaiExamEntity {
  @Id
  @Column(name = "exam_id", columnDefinition = "INT UNSIGNED")
  private Long id;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "exam_id", nullable = false, columnDefinition = "INT UNSIGNED")
  private ExamEntity exam;

  @Size(max = 255)
  @ColumnDefault("''")
  @Column(name = "comment")
  private String comment;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "recommendation", nullable = false, columnDefinition = "enum('ADMIT', 'REJECT')")
  private Recommendation recommendation;

}
