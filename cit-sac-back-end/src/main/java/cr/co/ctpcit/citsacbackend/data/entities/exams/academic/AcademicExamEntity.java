package cr.co.ctpcit.citsacbackend.data.entities.exams.academic;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.EnrollmentEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "tbl_academicexams")
public class AcademicExamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private EnrollmentEntity enrollment;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "exam_date")
    private Instant examDate;

    @NotNull
    @Column(name = "grade", nullable = false, precision = 5, scale = 2)
    private BigDecimal grade;

}