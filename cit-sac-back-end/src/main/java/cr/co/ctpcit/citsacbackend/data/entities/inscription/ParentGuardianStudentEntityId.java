package cr.co.ctpcit.citsacbackend.data.entities.inscription;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
    private static final long serialVersionUID = -3727431787214240435L;
    @Column(name = "student_id")
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Long studentId;

    @Column(name = "parentguardian_id")
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Long parentGuardianId;

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
        return Objects.hash(studentId, parentGuardianId);
    }

}