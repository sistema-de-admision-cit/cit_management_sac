package cr.co.ctpcit.citsacbackend.data.entities.exams.academic;

import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiExamQuestionsEntityId;
import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiExamsEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiQuestionsEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_academicexamquestions")
public class AcademicExamQuestionsEntity {
    @EmbeddedId
    private AcademicExamQuestionsEntityId id;

    @MapsId("examId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exam_id", nullable = false)
    @ToString.Exclude
    private AcademicQuestionsEntity examId;

    @MapsId("questionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    @ToString.Exclude
    private AcademicQuestionsEntity daiQuestions;

    @Column(name = "student_answer", columnDefinition = "TEXT")
    private String studentAnswer;
}
