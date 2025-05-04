package cr.co.ctpcit.citsacbackend.logic.dto.results;

import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) used to encapsulate the new status for updating a student's enrollment status.
 */
@Getter
@Setter
public class UpdateStatusDTO {

    /**
     * The new status to be applied to the student's enrollment.
     * This field cannot be null.
     */
    @NotNull
    private ProcessStatus newStatus;

}

