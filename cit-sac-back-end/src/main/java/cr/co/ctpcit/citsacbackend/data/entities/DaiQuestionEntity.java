package cr.co.ctpcit.citsacbackend.data.entities;

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
public class DaiQuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "question_grade", nullable = false, columnDefinition = "ENUM('1','2','3','4','5','6','7','8','9','10')")
    private Grades questionGrade;

    @Size(max = 255)
    @NotNull
    @Column(name = "question_text", nullable = false)
    private String questionText;

    @Size(max = 255)
    @Column(name = "image_url")
    private String imageUrl;

}