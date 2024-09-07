package cr.co.ctpcit.citsacbackend.data.entities.exams.dai;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_daiexamquestions")
public class DaiExamQuestionsEntity {

    @Column(name = "student_answer", columnDefinition = "TEXT")
    private String studentAnswer;



}
