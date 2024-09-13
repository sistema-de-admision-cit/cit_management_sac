package cr.co.ctpcit.citsacbackend.data.entities.inscription;

import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.KnownThrough;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Validated
@Table(name = "tbl_enrollments")
public class EnrollmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Long id;

    @NotNull(message = "Es obligatorio tener un estado de proceso")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProcessStatus status;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "enrollment_date", insertable = false, updatable = false)
    private Instant enrollmentDate;

    @NotNull(message = "Es obligatorio que se indique el grado al que se desea matricular")
    @Column(name = "grade_to_enroll", nullable = false)
    private Grades gradeToEnroll;

    @NotNull(message = "Es obligatorio que se indique cómo se enteró de nosotros")
    @Enumerated(EnumType.STRING)
    @Column(name = "known_through", nullable = false)
    private KnownThrough knownThrough;

    @NotNull(message = "Es obligatorio que se indique la fecha del examen")
    @Column(name = "exam_date", nullable = false)
    private LocalDate examDate;

    @NotNull(message = "Es obligatorio que se indique si se dio el consentimiento")
    @Column(name = "consent_given", nullable = false)
    @Builder.Default
    private Boolean consentGiven = false;

    @NotNull(message = "Es obligatorio que se indique si se dio el consentimiento para notificaciones por WhatsApp")
    @Column(name = "whatsapp_notification", nullable = false)
    @Builder.Default
    private Boolean whatsappNotification = false;

    @NotNull(message = "Es obligatorio que exista un estudiante asociado")
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    @ToString.Exclude
    private StudentEntity student;

}