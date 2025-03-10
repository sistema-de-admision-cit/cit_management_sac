package cr.co.ctpcit.citsacbackend.data.entities.exams;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "tbl_academic_exams")
public class AcademicExamEntity {
  @Id
  @Column(name = "exam_id", columnDefinition = "INT UNSIGNED")
  private Long id;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "exam_id", nullable = false, columnDefinition = "INT UNSIGNED")
  private ExamEntity exam;

  @NotNull
  @Column(name = "grade", nullable = false, precision = 5, scale = 2)
  private BigDecimal grade;

}
