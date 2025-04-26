package cr.co.ctpcit.citsacbackend.logic.dto.results;

import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusDTO {

    @NotNull
    private ProcessStatus newStatus;

}
