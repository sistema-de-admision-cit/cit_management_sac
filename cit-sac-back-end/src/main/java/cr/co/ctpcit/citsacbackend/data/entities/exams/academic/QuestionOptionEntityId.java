package cr.co.ctpcit.citsacbackend.data.entities.exams.academic;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class QuestionOptionEntityId implements java.io.Serializable {
    private static final long serialVersionUID = 2537592915128269509L;
    @NotNull
    @Column(name = "option_id", nullable = false)
    private Integer optionId;

    @NotNull
    @Column(name = "question_id", nullable = false)
    private Integer questionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        QuestionOptionEntityId entity = (QuestionOptionEntityId) o;
        return Objects.equals(this.questionId, entity.questionId) &&
                Objects.equals(this.optionId, entity.optionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, optionId);
    }

}