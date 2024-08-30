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
public class DaiGradeEntityId implements Serializable {
    private static final long serialVersionUID = -5501707686056852820L;
    @NotNull
    @Column(name = "daigrades_id", nullable = false)
    private Integer daigradesId;

    @NotNull
    @Column(name = "enrollment_id", nullable = false)
    private Integer enrollmentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DaiGradeEntityId entity = (DaiGradeEntityId) o;
        return Objects.equals(this.enrollmentId, entity.enrollmentId) &&
                Objects.equals(this.daigradesId, entity.daigradesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentId, daigradesId);
    }

}