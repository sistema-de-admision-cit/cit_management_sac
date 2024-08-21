package cr.co.ctpcit.sac.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_daiexams")
public class TblDaiExam {
    @EmbeddedId
    private TblDaiExamId id;

    @MapsId("enrollmentId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false, referencedColumnName = "enrollment_id")
    private TblEnrollment enrollment;

    @Column(name = "exam_date", nullable = false)
    private LocalDate examDate;

    @Column(name = "grade", nullable = false, precision = 5, scale = 2)
    private BigDecimal grade;

}