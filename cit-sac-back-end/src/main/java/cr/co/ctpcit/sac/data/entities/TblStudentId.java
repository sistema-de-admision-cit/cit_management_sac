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
public class TblStudentId implements Serializable {
    private static final long serialVersionUID = -2776451483289657890L;
    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @Column(name = "parent_guardian_id", nullable = false)
    private Integer parentGuardianId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TblStudentId entity = (TblStudentId) o;
        return Objects.equals(this.studentId, entity.studentId) &&
                Objects.equals(this.parentGuardianId, entity.parentGuardianId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, parentGuardianId);
    }

}