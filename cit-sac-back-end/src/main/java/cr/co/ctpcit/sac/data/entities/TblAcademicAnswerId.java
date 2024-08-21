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
public class TblAcademicAnswerId implements Serializable {
    private static final long serialVersionUID = 8766151006607708941L;
    @Column(name = "answer_id", nullable = false)
    private Integer answerId;

    @Column(name = "question_id", nullable = false)
    private Integer questionId;

    @Column(name = "exam_id", nullable = false)
    private Integer examId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TblAcademicAnswerId entity = (TblAcademicAnswerId) o;
        return Objects.equals(this.answerId, entity.answerId) &&
                Objects.equals(this.questionId, entity.questionId) &&
                Objects.equals(this.examId, entity.examId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answerId, questionId, examId);
    }

}