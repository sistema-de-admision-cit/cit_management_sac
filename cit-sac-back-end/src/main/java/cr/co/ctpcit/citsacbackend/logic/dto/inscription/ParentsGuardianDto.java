package cr.co.ctpcit.citsacbackend.logic.dto.inscription;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.ParentsGuardianEntity;
import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import cr.co.ctpcit.citsacbackend.data.enums.Relationship;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;

/**
 * DTO for {@link ParentsGuardianEntity}
 */
@Builder
public record ParentsGuardianDto(@NotNull @Size(max = 32) String firstName,
                                 @NotNull @Size(max = 32) String firstSurname, @Size(max = 32) String secondSurname,
                                 @NotNull IdType idType, @NotNull @Size(max = 20) String idNumber,
                                 @NotNull @Size(max = 20) String phoneNumber, @NotNull @Size(max = 100) String email,
                                 @NotNull @Size(max = 100) String homeAddress,
                                 @NotNull Relationship relationship) implements Serializable {
}