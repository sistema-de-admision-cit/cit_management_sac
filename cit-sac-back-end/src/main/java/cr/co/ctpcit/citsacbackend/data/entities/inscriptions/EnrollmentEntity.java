package cr.co.ctpcit.citsacbackend.data.entities.inscriptions;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import cr.co.ctpcit.citsacbackend.data.entities.exams.ExamEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.KnownThrough;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_enrollments")
public class EnrollmentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "enrollment_id", columnDefinition = "INT UNSIGNED")
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "student_id", nullable = false)
  private StudentEntity student;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false,
      columnDefinition = "enum('PENDING','ELIGIBLE','INELIGIBLE','ACCEPTED','REJECTED')")
  private ProcessStatus status;

  @CreationTimestamp
  @Column(name = "enrollment_date")
  private LocalDateTime enrollmentDate;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "grade_to_enroll", nullable = false,
      columnDefinition = "enum('FIRST', 'SECOND', 'THIRD', 'FOURTH', 'FIFTH', 'SIXTH', 'SEVENTH', 'EIGHTH', 'NINTH', 'TENTH')")
  private Grades gradeToEnroll;

  @NotNull
  @ColumnDefault("'OT'")
  @Enumerated(EnumType.STRING)
  @Column(name = "known_through", nullable = false,
      columnDefinition = "enum('SM', 'OH', 'FD', 'FM', 'OT')")
  private KnownThrough knownThrough;

  @NotNull
  @Column(name = "exam_date", nullable = false)
  private LocalDate examDate;

  @NotNull
  @ColumnDefault("0")
  @Column(name = "consent_given", nullable = false)
  @Builder.Default
  private Boolean consentGiven = false;

  @NotNull
  @ColumnDefault("0")
  @Column(name = "whatsapp_notification", nullable = false)
  @Builder.Default
  private Boolean whatsappNotification = false;

  @JsonManagedReference
  @Builder.Default
  @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<DocumentEntity> documents = new ArrayList<>();

  @JsonManagedReference
  @Builder.Default
  @OneToMany(mappedBy = "enrollment", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ExamEntity> exams = new ArrayList<>();

  public void addDocument(DocumentEntity document) {
    documents.add(document);
    document.setEnrollment(this);
  }

  public void removeDocument(DocumentEntity document) {
    documents.remove(document);
    document.setEnrollment(null);
  }

  public void addExam(ExamEntity exam) {
    exams.add(exam);
    exam.setEnrollment(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    EnrollmentEntity that = (EnrollmentEntity) o;

    return id.equals(that.id) && student.equals(
        that.student) && status == that.status && enrollmentDate.equals(
        that.enrollmentDate) && gradeToEnroll == that.gradeToEnroll && knownThrough.equals(
        that.knownThrough) && examDate.equals(that.examDate) && consentGiven.equals(
        that.consentGiven) && whatsappNotification.equals(that.whatsappNotification);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
