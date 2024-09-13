package cr.co.ctpcit.citsacbackend.logic.dto.inscription;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.inscription.AddressEntity}
 */
public record AddressDto(Long id,
                         @NotNull(message = "El país es obligatorio")
                         @Size(max = 16)
                         @NotBlank(message = "El país es obligatorio")
                         String country,
                         @NotNull(message = "La provincia es obligatoria")
                         @Size(max = 32)
                         @NotBlank(message = "La provincia es obligatoria")
                         String province,
                         @NotNull(message = "La ciudad es obligatoria")
                         @Size(max = 32)
                         @NotBlank(message = "La ciudad es obligatoria")
                         String city,
                         @NotNull(message = "El distrito es obligatorio")
                         @Size(max = 32)
                         @NotBlank(message = "El distrito es obligatorio")
                         String district,
                         @NotNull(message = "La dirección es obligatoria")
                         @Size(max = 64)
                         @NotBlank(message = "La dirección es obligatoria")
                         String addressInfo) implements Serializable {
}