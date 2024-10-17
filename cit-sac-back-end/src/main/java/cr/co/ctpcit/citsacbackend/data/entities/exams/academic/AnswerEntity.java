package cr.co.ctpcit.citsacbackend.data.entities.exams.academic;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_answer")
public class AnswerEntity {
    @Id
    @Column(name = "answer_id", nullable = false)
    private Integer answerId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
        @JoinColumn(name = "question_id", referencedColumnName = "question_id", nullable = false),
        @JoinColumn(name = "exam_id", referencedColumnName = "exam_id", nullable = false)
    })
    private AcademicExamQuestionEntity academicExamQuestion;

    @Column(name = "option_selected")
    private Integer optionSelected;

}