package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.ParentEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity}
 */
public record StudentDto(Long id, PersonDto person, @NotNull LocalDate birthDate,
                         @Size(max = 128) String previousSchool, @NotNull Boolean hasAccommodations,
                         List<ParentEntity> parentParents) implements Serializable {
}
