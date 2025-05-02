package cr.co.ctpcit.citsacbackend.logic.dto.configs;

import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * Represents the Data Transfer Object (DTO) for system configuration settings.
 * Contains information about the configuration name and its value.
 */
public record SystemConfigDto(Integer id, Configurations configName,
                              @NotNull @Size(max = 128) String configValue)
    implements Serializable {
}
