package cr.co.ctpcit.sac.data.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_daianswers")
public class DaiAnswer {
    @EmbeddedId
    private DaiAnswerId id;

    @MapsId("questionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private DaiQuestion question;

    @MapsId("examId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exam_id", nullable = false, referencedColumnName = "exam_id")
    private DaiExam exam;

    @Lob
    @Column(name = "student_answer", nullable = false)
    private String studentAnswer;

}