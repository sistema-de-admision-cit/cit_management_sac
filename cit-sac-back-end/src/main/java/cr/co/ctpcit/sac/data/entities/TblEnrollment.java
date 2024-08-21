package cr.co.ctpcit.sac.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_enrollments")
public class TblEnrollment {
    @EmbeddedId
    private TblEnrollmentId id;

    @MapsId("studentId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false, referencedColumnName = "student_id")
    private TblStudent student;

    @Lob
    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;

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

}