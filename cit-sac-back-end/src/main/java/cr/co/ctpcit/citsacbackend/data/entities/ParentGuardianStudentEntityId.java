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
public class ParentGuardianStudentEntityId implements Serializable {
    private static final long serialVersionUID = -4107909645072191447L;
    @NotNull
    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @NotNull
    @Column(name = "parentguardian_id", nullable = false)
    private Integer parentGuardianId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ParentGuardianStudentEntityId entity = (ParentGuardianStudentEntityId) o;
        return Objects.equals(this.studentId, entity.studentId) &&
                Objects.equals(this.parentGuardianId, entity.parentGuardianId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, parentguardianId);
    }

}