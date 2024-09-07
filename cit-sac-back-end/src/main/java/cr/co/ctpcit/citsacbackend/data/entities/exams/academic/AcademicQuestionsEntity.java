package cr.co.ctpcit.citsacbackend.data.entities.exams.academic;
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
@Table(name = "tbl_academicquestions")
public class AcademicQuestionsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", nullable = false)
    private Integer id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "question_grade", nullable = false)
    private Grades questionGrade;

    @Size(max = 255)
    @NotNull
    @Column(name = "option_a", nullable = false)
    private String optionA;

    @Size(max = 255)
    @NotNull
    @Column(name = "option_b", nullable = false)
    private String optionB;

    @Size(max = 255)
    @NotNull
    @Column(name = "option_c", nullable = false)
    private String optionC;

    @Size(max = 255)
    @NotNull
    @Column(name = "option_d", nullable = false)
    private String optionD;

    @NotNull
    @Column(name = "correct_option", nullable = false, length = 1)
    private String correctOption;

    @Size(max = 255)
    @NotNull
    @Column(name = "question_text", nullable = false)
    private String questionText;

    @Size(max = 255)
    @Column(name = "image_url", nullable = false)
    private String imageUrl;


}
