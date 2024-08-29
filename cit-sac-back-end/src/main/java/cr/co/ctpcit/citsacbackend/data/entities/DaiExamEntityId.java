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
public class DaiExamEntityId implements Serializable {
    private static final long serialVersionUID = 3155421972053196595L;
    @NotNull
    @Column(name = "exam_id", nullable = false)
    private Integer examId;

    @NotNull
    @Column(name = "daigrades_id", nullable = false)
    private Integer daiGradesId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DaiExamEntityId entity = (DaiExamEntityId) o;
        return Objects.equals(this.daiGradesId, entity.daiGradesId) &&
                Objects.equals(this.examId, entity.examId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(daiGradesId, examId);
    }

}