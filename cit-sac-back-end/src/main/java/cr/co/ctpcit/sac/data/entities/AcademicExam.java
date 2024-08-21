package cr.co.ctpcit.sac.data.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_academicexams")
public class AcademicExam {
    @EmbeddedId
    private AcademicExamId id;

    @MapsId("enrollmentId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false, referencedColumnName = "enrollment_id")
    private Enrollment enrollment;

    @Column(name = "exam_date", nullable = false, updatable = false, insertable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Instant examDate;

    @Column(name = "grade", nullable = false, precision = 5, scale = 2)
    private BigDecimal grade;

    @OneToMany(mappedBy = "question", orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<AcademicAnswer> academicAnswers;

    public void addAcademicAnswer(AcademicAnswer answer) {
        if (academicAnswers == null) return;
        academicAnswers.add(answer);
        answer.setExam(this);
    }
}