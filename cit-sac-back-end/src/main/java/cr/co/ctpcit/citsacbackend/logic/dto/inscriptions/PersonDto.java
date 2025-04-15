package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;

/**
 * Data Transfer Object (DTO) representing the personal information of an individual.
 * Includes the person's name, surname(s), identification type, and ID number.
 */
@Builder
public record PersonDto(Long id, @NotNull @Size(max = 32) String firstName,
                        @NotNull @Size(max = 32) String firstSurname,
                        @Size(max = 32) String secondSurname, @NotNull IdType idType,
                        @NotNull @Size(max = 32) String idNumber) implements Serializable {
}
