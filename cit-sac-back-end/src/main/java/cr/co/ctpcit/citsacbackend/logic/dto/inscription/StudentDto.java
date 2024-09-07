package cr.co.ctpcit.citsacbackend.logic.dto;

import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.StudentEntity}
 */
public record StudentDto(@NotNull @Size(max = 32) String firstName, @NotNull @Size(max = 32) String firstSurname,
                         @Size(max = 32) String secondSurname, @NotNull LocalDate birthDate, @NotNull IdType idType,
                         @NotNull @Size(max = 20) String idNumber, @Size(max = 100) String previousSchool,
                         @NotNull Boolean hasAccommodations) implements Serializable {
}