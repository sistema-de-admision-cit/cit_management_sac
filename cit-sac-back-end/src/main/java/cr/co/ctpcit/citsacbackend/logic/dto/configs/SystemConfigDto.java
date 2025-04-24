package cr.co.ctpcit.citsacbackend.logic.dto.configs;

import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.configs.SystemConfigEntity}
 */
public record SystemConfigDto(Integer id, Configurations configName,
                              @NotNull @Size(max = 128) String configValue, Boolean isSensible)
    implements Serializable {
}
