package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.AddressEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;

/**
 * Represents the Data Transfer Object (DTO) for an address.
 * Contains details about the country, province, city, district, and address information.
 */
@Builder
public record AddressDto(Long id, @NotNull @Size(max = 16) String country,
                         @NotNull @Size(max = 32) String province,
                         @NotNull @Size(max = 32) String city,
                         @NotNull @Size(max = 32) String district,
                         @NotNull @Size(max = 128) String addressInfo) implements Serializable {
}
