package cr.co.ctpcit.citsacbackend.logic.dto.exam;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;

@Builder
public record ExamQuestionsDto(Long id,
                              @NotNull Long examId,
                              @NotNull QuestionDto question,
                              String answer) implements Serializable {
}

