package cr.co.ctpcit.citsacbackend.data.entities.exams;

import cr.co.ctpcit.citsacbackend.data.enums.EnglishLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_english_exams")
public class EnglishExamEntity {
  @Id
  @Column(name = "exam_id", columnDefinition = "INT UNSIGNED")
  private Long id;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "exam_id", nullable = false, columnDefinition = "INT UNSIGNED")
  private ExamEntity exam;

  @Column(name = "tracktest_id", columnDefinition = "INT UNSIGNED")
  private Long trackTestId;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "level", nullable = false,
      columnDefinition = "enum('A1', 'A2', 'B1', 'B2', 'C1', 'C2')")
  private EnglishLevel level;

  @NotNull
  @Column(name = "core", nullable = false)
  private Byte core;


}
