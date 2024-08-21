package cr.co.ctpcit.sac.data.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_daiexamquestions")
public class DaiExamQuestion {
    @EmbeddedId
    private DaiExamQuestionId id;

    @MapsId("examId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exam_id", nullable = false, referencedColumnName = "exam_id")
    private DaiExam exam;

    @MapsId("questionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private DaiQuestion question;

}