package cr.co.ctpcit.citsacbackend.data.entities.exams.academic;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_academicexamquestions")
public class AcademicExamQuestionEntity {
    @EmbeddedId
    private AcademicExamQuestionEntityId id;

    @MapsId("examId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exam_id", nullable = false)
    private AcademicExamEntity exam;

    @MapsId("questionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private AcademicQuestionEntity question;

}