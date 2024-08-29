package cr.co.ctpcit.citsacbackend.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_enrollments")
public class EnrollmentEntity {
    @EmbeddedId
    private EnrollmentEntityId id;

    @MapsId("studentId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;

    @ColumnDefault("_utf8mb4'pending'")
    @Column(name = "status")
    private String status;

    @NotNull
    @Column(name = "enrollment_date", nullable = false)
    private Instant enrollmentDate;

    @NotNull
    @Column(name = "grade_to_enroll", nullable = false)
    private String gradeToEnroll;

    @NotNull
    @Column(name = "known_through", nullable = false)
    private String knownThrough;

    @NotNull
    @Column(name = "exam_date", nullable = false)
    private LocalDate examDate;

    @NotNull
    @Column(name = "consent_given", nullable = false)
    private Boolean consentGiven = false;

    @NotNull
    @Column(name = "whatsapp_notification", nullable = false)
    private Boolean whatsappNotification = false;

}