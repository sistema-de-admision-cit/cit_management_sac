package cr.co.ctpcit.citsacbackend.logic.dto.inscription;

import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import cr.co.ctpcit.citsacbackend.data.enums.Relationship;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.inscription.ParentsGuardianEntity}
 */
@Builder
public record ParentsGuardianDto(Long id, @NotNull(message = "El nombre es obligatorio") @NotBlank(
    message = "El nombre es obligatorio") @Size(max = 32) String firstName,
                                 @NotNull(message = "El primer apellido es obligatorio") @NotBlank(
                                     message = "El primer apellido es obligatorio") @Size(
                                     max = 32) String firstSurname,
                                 @Size(max = 32) String secondSurname, @NotNull(
    message = "El tipo de identificación es obligatorio") IdType idType, @NotNull(
    message = "El número de identificación es obligatorio") @NotBlank(
    message = "El número de identificación es obligatorio") @Size(max = 20) String idNumber,
                                 @NotNull(
                                     message = "El número de teléfono es obligatorio") @NotBlank(
                                     message = "El número de teléfono es obligatorio") @Size(
                                     max = 20) String phoneNumber, @NotNull(
    message = "El correo electrónico es obligatorio") @NotBlank(
    message = "El correo electrónico es obligatorio") @Size(max = 100) String email, @NotNull(
    message = "La relación con el estudiante es obligatoria") Relationship relationship, @NotEmpty(
    message = "La lista de direcciones no puede estar vacía") List<AddressDto> addresses)
    implements Serializable {
}
