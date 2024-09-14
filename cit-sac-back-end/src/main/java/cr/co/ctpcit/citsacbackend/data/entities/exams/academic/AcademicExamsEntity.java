package cr.co.ctpcit.citsacbackend.data.entities.exams.academic;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.EnrollmentEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_academicexams")
public class AcademicExamsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false)
    @ToString.Exclude
    private EnrollmentEntity enrollment;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "exam_date", nullable = false)
    private Date examDate;

    @NotNull
    @Column(name = "grade", precision = 5, scale = 2, nullable = false)
    private BigDecimal grade;
}
