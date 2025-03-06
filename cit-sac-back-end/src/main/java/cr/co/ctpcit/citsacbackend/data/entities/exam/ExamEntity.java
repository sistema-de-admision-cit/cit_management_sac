package cr.co.ctpcit.citsacbackend.data.entities.exam;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_exams")
public class ExamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id", columnDefinition = "INT UNSIGNED")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false, columnDefinition = "INT UNSIGNED")
    private EnrollmentEntity enrollment;

    @NotNull
    @CreationTimestamp
    @Column(name = "exam_date", nullable = false, updatable = false)
    private Instant examDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "exam_type", nullable = false, columnDefinition = "enum('ACA', 'DAI')")
    private  QuestionType examType;

    @Column(name = "responses", columnDefinition = "JSON")
    private String responses;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private List<ExamQuestionsEntity> examQuestions = new ArrayList<>();

    public void addExamQuestion(ExamQuestionsEntity examQuestion) {
        examQuestions.add(examQuestion);
        examQuestion.setExam(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamEntity that = (ExamEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}