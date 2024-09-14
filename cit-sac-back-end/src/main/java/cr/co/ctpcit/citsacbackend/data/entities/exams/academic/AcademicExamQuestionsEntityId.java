package cr.co.ctpcit.citsacbackend.data.entities.exams.academic;

import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiExamQuestionsEntityId;
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
public class AcademicExamQuestionsEntityId implements Serializable {

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
        AcademicExamQuestionsEntityId entity = (AcademicExamQuestionsEntityId) o;
        return Objects.equals(this.examId, entity.examId) &&
                Objects.equals(this.questionId, entity.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(examId, questionId);
    }


}
