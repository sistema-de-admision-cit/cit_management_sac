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
public class AnswerEntityId implements java.io.Serializable {
    private static final long serialVersionUID = -4462251330431057853L;
    @NotNull
    @Column(name = "answer_id", nullable = false)
    private Integer answerId;

    @NotNull
    @Column(name = "exam_id", nullable = false)
    private Integer examId;

    @NotNull
    @Column(name = "question_id", nullable = false)
    private Integer questionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AnswerEntityId entity = (AnswerEntityId) o;
        return Objects.equals(this.answerId, entity.answerId) &&
                Objects.equals(this.questionId, entity.questionId) &&
                Objects.equals(this.examId, entity.examId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId, questionId, examId);
    }

}