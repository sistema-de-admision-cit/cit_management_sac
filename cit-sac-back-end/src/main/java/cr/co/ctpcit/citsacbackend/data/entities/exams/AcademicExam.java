package cr.co.ctpcit.citsacbackend.data.entities.exams;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "tbl_academic_exams")
public class AcademicExam {
  @Id
  @Column(name = "exam_id", columnDefinition = "INT UNSIGNED")
  private Long id;

  @NotNull
  @Column(name = "grade", nullable = false, precision = 5, scale = 2)
  private BigDecimal grade;

}
