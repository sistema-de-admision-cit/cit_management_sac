package cr.co.ctpcit.citsacbackend.data.entities.exams.english;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.EnglishLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;


@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_englishexams")
public class EnglishExamEntity {
  @Id
  @Column(name = "exam_id", nullable = false)
  private Integer id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "enrollment_id", nullable = false)
  private EnrollmentEntity enrollment;

  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "exam_date")
  private Instant examDate;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "level", nullable = false)
  private EnglishLevel level;

  @NotNull
  @Column(name = "grade", nullable = false, precision = 5, scale = 2)
  private BigDecimal grade;

}
