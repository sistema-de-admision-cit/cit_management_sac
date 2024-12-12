package cr.co.ctpcit.citsacbackend.logic.dto.exams.academic;

import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.exams.academic.AcademicQuestionEntity}
 */
public record AcademicQuestionEntityDto(Integer id, @NotNull @Size(max = 255) String questionGrade,
                                        @NotNull @Size(max = 255) String questionText,
                                        @Size(max = 255) String imageUrl,
                                        @NotNull QuestionType questionType)
    implements Serializable {
}
