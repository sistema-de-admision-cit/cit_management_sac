package cr.co.ctpcit.sac.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class TblExamDayId implements Serializable {
    private static final long serialVersionUID = 7612432024794177774L;
    @Column(name = "exam_day_id", nullable = false)
    private Integer examDayId;

    @Column(name = "exam_period_id", nullable = false)
    private Integer examPeriodId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TblExamDayId entity = (TblExamDayId) o;
        return Objects.equals(this.examPeriodId, entity.examPeriodId) &&
                Objects.equals(this.examDayId, entity.examDayId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(examPeriodId, examDayId);
    }

}