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
public class EnglishExamId implements Serializable {
    private static final long serialVersionUID = 991130779159880819L;
    @Column(name = "exam_id", nullable = false)
    private Integer examId;

    @Column(name = "enrollment_id", nullable = false)
    private Integer enrollmentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EnglishExamId entity = (EnglishExamId) o;
        return Objects.equals(this.enrollmentId, entity.enrollmentId) &&
                Objects.equals(this.examId, entity.examId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentId, examId);
    }

}