package cr.co.ctpcit.citsacbackend.logic.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

public record SystemConfigDto(
        Integer id,
        @NotNull @Size(max = 100) String configName,
        @NotNull @Size(max = 255) String configValue
) implements Serializable {
}
