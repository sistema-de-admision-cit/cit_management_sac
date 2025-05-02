package cr.co.ctpcit.citsacbackend.logic.dto.exams.dai;

import cr.co.ctpcit.citsacbackend.data.enums.Recommendation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;

/**
 * Represents the Data Transfer Object (DTO) for updating a DAI exam.
 * Contains information about the updated comment and recommendation for the DAI exam.
 */
@Builder
public record UpdateDaiExamDto(Long id, @Size(max = 255) String comment,
                               @NotNull Recommendation recommendation) implements Serializable {
}
