package cr.co.ctpcit.citsacbackend.logic.dto.exam;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Builder
public record ExamDto(Long id,
                      @NotNull Long enrollmentId,
                      @NotNull Instant examDate,
                      @NotNull QuestionType examType,
                      String responses,
                      List<ExamQuestionsDto> examQuestions) implements Serializable {
}