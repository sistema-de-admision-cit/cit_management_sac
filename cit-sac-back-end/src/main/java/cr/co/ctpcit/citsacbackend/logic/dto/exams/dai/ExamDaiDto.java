package cr.co.ctpcit.citsacbackend.logic.dto.exams.dai;

import cr.co.ctpcit.citsacbackend.data.enums.ExamType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Builder
public record ExamDaiDto(Long id, @NotNull Long enrollment, @NotNull Instant examDate,
                         @NotNull ExamType examType, List<QuestionDaiDto> responses)
    implements Serializable {
}
