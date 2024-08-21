package cr.co.ctpcit.sac.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

/**
 * Mapping for DB view
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Immutable
@Table(name = "vw_grades")
public class GradeView {
    @Column(name = "enrollment_id", nullable = false)
    private Integer enrollmentId;

    @Column(name = "exam_type", nullable = false, length = 8)
    private String examType;

    @Column(name = "grade", nullable = false, precision = 5, scale = 2)
    private BigDecimal grade;

}