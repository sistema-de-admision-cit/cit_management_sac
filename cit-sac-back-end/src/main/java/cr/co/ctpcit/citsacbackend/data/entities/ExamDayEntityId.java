package cr.co.ctpcit.citsacbackend.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Embeddable
public class ExamDayEntityId implements Serializable {
    private static final long serialVersionUID = 6067137268647062724L;
    @NotNull
    @Column(name = "exam_day_id", nullable = false)
    private Integer examDayId;

    @NotNull
    @Column(name = "exam_period_id", nullable = false)
    private Integer examPeriodId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ExamDayEntityId entity = (ExamDayEntityId) o;
        return Objects.equals(this.examPeriodId, entity.examPeriodId) &&
                Objects.equals(this.examDayId, entity.examDayId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(examPeriodId, examDayId);
    }

}