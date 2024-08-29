package cr.co.ctpcit.citsacbackend.data.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_daiexamquestions")
public class DaiExamQuestionEntity {
    @EmbeddedId
    private DaiExamQuestionEntityId id;

    @MapsId("examId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exam_id", nullable = false, referencedColumnName = "exam_id")
    private DaiExamEntity exam;

    @MapsId("questionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private DaiQuestionEntity question;

    @Lob
    @Column(name = "student_answer")
    private String studentAnswer;

}