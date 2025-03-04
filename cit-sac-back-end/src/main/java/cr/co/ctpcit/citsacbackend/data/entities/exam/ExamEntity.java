package cr.co.ctpcit.citsacbackend.data.entities.exam;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_Exams")
public class ExamEntity {
    @Id
    @Column(name = "exam_id", columnDefinition = "INT UNSIGNED")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_exam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enrollment_id", nullable = false, foreignKey = @ForeignKey(name = "FK_Exams_Enrollments"))
    private EnrollmentEntity enrollment;

    @NotNull
    @Column(name = "exam_id", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant exam_date = Instant.now();

    @NotNull //Cambiar tipo
    @Enumerated(EnumType.STRING)
    @Column(name = "exam_type", nullable = false, columnDefinition = "enum('ACA','DAI')")
    private QuestionType exam_type;

    @Column(name = "responses", columnDefinition = "JSON DEFAULT NULL")
    private String responses;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ExamEntity that = (ExamEntity) o;
        return Objects.equals(id_exam, that.id_exam) && Objects.equals(enrollment, that.enrollment) && Objects.equals(exam_date, that.exam_date) && exam_type == that.exam_type && Objects.equals(responses, that.responses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_exam, enrollment, exam_date, exam_type, responses);
    }
}



