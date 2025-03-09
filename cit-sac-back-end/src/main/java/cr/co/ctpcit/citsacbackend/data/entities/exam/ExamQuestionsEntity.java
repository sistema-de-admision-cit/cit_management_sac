package cr.co.ctpcit.citsacbackend.data.entities.exam;
import com.fasterxml.jackson.annotation.JsonBackReference;
import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_exam_questions")
public class ExamQuestionsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_question_id", columnDefinition = "INT UNSIGNED")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exam_id", nullable = false, columnDefinition = "INT UNSIGNED")
    @JsonBackReference
    private ExamEntity exam;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private QuestionEntity question;

    @Column(name = "answer", columnDefinition = "TEXT")
    private String answer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamQuestionsEntity that = (ExamQuestionsEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
