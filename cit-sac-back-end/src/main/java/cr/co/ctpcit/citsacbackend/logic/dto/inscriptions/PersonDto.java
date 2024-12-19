package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity}
 */
public record PersonDto(Long id, @NotNull @Size(max = 32) String firstName,
                        @NotNull @Size(max = 32) String firstSurname,
                        @Size(max = 32) String secondSurname, @NotNull IdType idType,
                        @NotNull @Size(max = 32) String idNumber) implements Serializable {
}
