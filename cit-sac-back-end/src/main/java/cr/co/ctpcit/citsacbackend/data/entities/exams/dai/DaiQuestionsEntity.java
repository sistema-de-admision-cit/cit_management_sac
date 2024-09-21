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
@Table(name = "tbl_daiquestions")
public class DaiQuestionsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "question_text", nullable = false)
    private String questionText;


    @NotNull
    @Column(name = "question_grade", nullable = false)
    private Grades questionGrade;

    @Size(max = 255)
    @Column(name = "image_url", nullable = true)
    private String imageUrl;
}
