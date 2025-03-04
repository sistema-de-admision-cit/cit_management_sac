package cr.co.ctpcit.citsacbackend.data.entities.exam;
import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionOptionEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_exam_questions")
public class ExamQuestionsEntity {
    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exam_id", nullable = false)
    private ExamEntity exam_id;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selected_option_id", nullable = false, foreignKey = @ForeignKey(name = "FK_Questions_Options"))
    private QuestionOptionEntity options;

    @Column(name = "answer_text", columnDefinition = "TEXT")
    private String answerText;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ExamQuestionsEntity that = (ExamQuestionsEntity) o;
        return Objects.equals(exam_id, that.exam_id) && Objects.equals(question_id, that.question_id) && Objects.equals(options, that.options) && Objects.equals(answerText, that.answerText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exam_id, question_id, options, answerText);
    }
}
