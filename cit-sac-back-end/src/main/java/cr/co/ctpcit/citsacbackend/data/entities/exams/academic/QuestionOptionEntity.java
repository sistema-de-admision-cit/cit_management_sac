package cr.co.ctpcit.citsacbackend.data.entities.exams.academic;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_questionoption")
public class QuestionOptionEntity {
    @EmbeddedId
    private QuestionOptionEntityId id;

    @MapsId("questionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private AcademicQuestionEntity question;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Size(max = 255)
    @Column(name = "`option`")
    private String option;

}