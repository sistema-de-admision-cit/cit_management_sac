package cr.co.ctpcit.sac.data.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_academicanswers")
public class AcademicAnswer {
    @EmbeddedId
    private AcademicAnswerId id;

    @MapsId("questionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private AcademicQuestion question;

    @MapsId("examId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exam_id", nullable = false, referencedColumnName = "exam_id")
    private AcademicExam exam;

    @Column(name = "student_answer", nullable = false)
    private Character studentAnswer;

}