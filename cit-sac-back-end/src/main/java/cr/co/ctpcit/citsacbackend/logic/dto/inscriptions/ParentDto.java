package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.ParentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.Relationship;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing the details of a parent.
 * Contains personal information, contact details, relationship information, and addresses.
 */
@Builder
public record ParentDto(Long id, PersonDto person, @NotNull @Size(max = 32) String phoneNumber,
                        @NotNull @Size(max = 64) String email, @NotNull Relationship relationship,
                        List<AddressDto> addresses) implements Serializable {
}
