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
public class EnrollmentEntityId implements Serializable {
    private static final long serialVersionUID = 2711250573381120141L;
    @NotNull
    @Column(name = "enrollment_id", nullable = false)
    private Integer enrollmentId;

    @NotNull
    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EnrollmentEntityId entity = (EnrollmentEntityId) o;
        return Objects.equals(this.studentId, entity.studentId) &&
                Objects.equals(this.enrollmentId, entity.enrollmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, enrollmentId);
    }

}