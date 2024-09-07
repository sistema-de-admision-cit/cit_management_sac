package cr.co.ctpcit.citsacbackend.data.entities.inscription;

import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.KnownThrough;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
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
@ToString
@Entity
@Table(name = "tbl_enrollments")
public class EnrollmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    @ToString.Exclude
    private StudentEntity student;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProcessStatus status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "enrollment_date")
    private Instant enrollmentDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "grade_to_enroll", nullable = false)
    private Grades gradeToEnroll;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "known_through", nullable = false)
    private KnownThrough knownThrough;

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