package cr.co.ctpcit.sac.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_academicquestions")
public class AcademicQuestion {
    @Id
    @Column(name = "question_id", nullable = false)
    private Integer id;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "option_a", nullable = false)
    private String optionA;

    @Column(name = "option_b", nullable = false)
    private String optionB;

    @Column(name = "option_c", nullable = false)
    private String optionC;

    @Column(name = "option_d", nullable = false)
    private String optionD;

    @Column(name = "correct_option", nullable = false)
    private Character correctOption;

    @OneToMany(mappedBy = "question", orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<AcademicAnswer> academicAnswers;

    public void addAcademicAnswer(AcademicAnswer answer) {
        if (academicAnswers == null) return;
        academicAnswers.add(answer);
        answer.setQuestion(this);
    }
}