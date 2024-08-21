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
public class EnrollmentId implements Serializable {
    private static final long serialVersionUID = -8922741638305968429L;
    @Column(name = "enrollment_id", nullable = false)
    private Integer enrollmentId;

    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EnrollmentId entity = (EnrollmentId) o;
        return Objects.equals(this.studentId, entity.studentId) &&
                Objects.equals(this.enrollmentId, entity.enrollmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, enrollmentId);
    }

}