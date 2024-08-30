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
public class DaiInterviewEntityId implements Serializable {
    private static final long serialVersionUID = -4351702624109756477L;
    @NotNull
    @Column(name = "interview_id", nullable = false)
    private Integer interviewId;

    @NotNull
    @Column(name = "daigrades_id", nullable = false)
    private Integer daiGradesId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DaiInterviewEntityId entity = (DaiInterviewEntityId) o;
        return Objects.equals(this.interviewId, entity.interviewId) &&
                Objects.equals(this.daiGradesId, entity.daiGradesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interviewId, daiGradesId);
    }

}