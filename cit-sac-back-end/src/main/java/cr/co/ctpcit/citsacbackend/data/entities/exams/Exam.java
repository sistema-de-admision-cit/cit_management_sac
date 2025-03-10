package cr.co.ctpcit.citsacbackend.data.entities.exams;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.ExamType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "tbl_exams")
public class Exam {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "exam_id", columnDefinition = "INT UNSIGNED")
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "enrollment_id", nullable = false)
  private EnrollmentEntity enrollment;

  @NotNull
  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "exam_date", nullable = false)
  private Instant examDate;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "exam_type", nullable = false, columnDefinition = "enum('ACA', 'DAI', 'ENG')")
  private ExamType examType;

  @Column(name = "responses")
  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, Object> responses;

}
