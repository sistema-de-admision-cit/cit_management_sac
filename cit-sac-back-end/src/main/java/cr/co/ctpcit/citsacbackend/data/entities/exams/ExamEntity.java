package cr.co.ctpcit.citsacbackend.data.entities.exams;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "tbl_exams")
public class ExamEntity {
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

  @JsonIgnore
  @OneToOne(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
  private EnglishExamEntity englishExam;

  @JsonIgnore
  @OneToOne(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
  private DaiExamEntity daiExam;

  @JsonIgnore
  @OneToOne(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
  private AcademicExamEntity academicExam;

  public void addEnglishExam(EnglishExamEntity englishExam) {
    this.englishExam = englishExam;
    englishExam.setExam(this);
  }

  public void addDaiExam(DaiExamEntity daiExam) {
    this.daiExam = daiExam;
    daiExam.setExam(this);
  }

  public void addAcademicExam(AcademicExamEntity academicExam) {
    this.academicExam = academicExam;
    academicExam.setExam(this);
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass())
      return false;
    ExamEntity that = (ExamEntity) o;
    return Objects.equals(id, that.id) && Objects.equals(enrollment,
        that.enrollment) && Objects.equals(examDate, that.examDate) && examType == that.examType;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
