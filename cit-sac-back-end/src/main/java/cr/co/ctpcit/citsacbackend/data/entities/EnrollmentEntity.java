package cr.co.ctpcit.citsacbackend.data.entities;

import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.KnownThrough;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "ENUM('P','E','I','A','R')")
    private ProcessStatus status;

    @NotNull
    @Column(name = "enrollment_date", nullable = false)
    private Instant enrollmentDate;

    @NotNull
    @Column(name = "grade_to_enroll", nullable = false)
    private Grades gradeToEnroll;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "known_through", nullable = false, columnDefinition = "ENUM('SM','OH','FD','FM','OT')")
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