package cr.co.ctpcit.citsacbackend.data.entities.exams.academic;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_answer")
public class AnswerEntity {
    @EmbeddedId
    private AnswerEntityId id;

    @MapsId("id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private AcademicExamQuestionEntity tblAcademicexamquestions;

    @Column(name = "option_selected")
    private Integer optionSelected;

}