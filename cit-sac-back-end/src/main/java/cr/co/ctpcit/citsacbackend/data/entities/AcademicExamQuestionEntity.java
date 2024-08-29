package cr.co.ctpcit.citsacbackend.data.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_academicexamquestions")
public class AcademicExamQuestionEntity {
    @EmbeddedId
    private AcademicExamQuestionEntityId id;

    @MapsId("examId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exam_id", nullable = false, referencedColumnName = "exam_id")
    private AcademicExamEntity exam;

    @MapsId("questionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private AcademicQuestionEntity question;

    @Column(name = "student_answer")
    private Character studentAnswer;

}