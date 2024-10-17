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
public class AcademicExamQuestionEntityId implements java.io.Serializable {
    private static final long serialVersionUID = 58526246259846607L;
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
        AcademicExamQuestionEntityId entity = (AcademicExamQuestionEntityId) o;
        return Objects.equals(this.questionId, entity.questionId) &&
                Objects.equals(this.examId, entity.examId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, examId);
    }

}