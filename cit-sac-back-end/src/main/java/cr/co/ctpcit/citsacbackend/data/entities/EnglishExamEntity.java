package cr.co.ctpcit.citsacbackend.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_englishexams")
public class EnglishExamEntity {
    @EmbeddedId
    private EnglishExamEntityId id;

    @MapsId("enrollmentId")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false, referencedColumnName = "enrollment_id")
    private EnrollmentEntity enrollment;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "exam_date")
    private Instant examDate;

    @NotNull
    @Column(name = "grade", nullable = false, precision = 5, scale = 2)
    private BigDecimal grade;

}