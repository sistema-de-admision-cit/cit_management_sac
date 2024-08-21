package cr.co.ctpcit.sac.data.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_enrollments")
public class Enrollment {
    @EmbeddedId
    private EnrollmentId id;

    @MapsId("studentId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false, referencedColumnName = "student_id")
    private Student student;

    @Lob
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "exam_date", nullable = false, updatable = false, insertable = false)
    @ColumnDefault("CURRENT_TIMESTAMP")
    @CreationTimestamp
    private Instant enrollmentDate;

    @Lob
    @Column(name = "grade_to_enroll", nullable = false)
    private String gradeToEnroll;

    @Lob
    @Column(name = "known_through", nullable = false)
    private String knownThrough;

    @Column(name = "exam_date", nullable = false)
    private LocalDate examDate;

    @Column(name = "consent_given", nullable = false)
    private Boolean consentGiven = false;

    @Column(name = "whatsapp_notification", nullable = false)
    private Boolean whatsappNotification = false;

    @OneToMany(mappedBy = "enrollment", orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<AcademicExam> academicExams;

}