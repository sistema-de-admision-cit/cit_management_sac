package cr.co.ctpcit.citsacbackend.data.entities.exams.dai;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_daiexamquestions")
public class DaiExamQuestionsEntity {
    @EmbeddedId
    private DaiExamQuestionsEntityId id;

    @MapsId("examId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exam_id", nullable = false)
    @ToString.Exclude
    private DaiExamsEntity examId;

    @MapsId("questionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    @ToString.Exclude
    private DaiQuestionsEntity daiQuestions;

    @Column(name = "student_answer", columnDefinition = "TEXT")
    private String studentAnswer;
}
