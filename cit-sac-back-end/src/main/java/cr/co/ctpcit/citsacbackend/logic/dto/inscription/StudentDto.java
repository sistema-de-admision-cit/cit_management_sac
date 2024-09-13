package cr.co.ctpcit.citsacbackend.logic.dto.inscription;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.StudentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link StudentEntity}
 */
@Builder
public record StudentDto(Long id,
                         @NotEmpty
                         List<EnrollmentDto> enrollments,
                         @NotEmpty
                         List<ParentsGuardianDto> parents,
                         @NotNull(message = "El nombre es obligatorio")
                         @NotBlank(message = "El nombre es obligatorio")
                         @Size(max = 32)
                         String firstName,
                         @NotNull(message = "El primer apellido es obligatorio")
                         @NotBlank(message = "El primer apellido es obligatorio")
                         @Size(max = 32)
                         String firstSurname,
                         @Size(max = 32)
                         String secondSurname,
                         @NotNull(message = "La fecha de nacimiento es obligatoria")
                         LocalDate birthDate,
                         @NotNull(message = "El tipo de identificación es obligatorio")
                         IdType idType,
                         @NotNull(message = "El número de identificación es obligatorio")
                         @NotBlank(message = "El número de identificación es obligatorio")
                         @Size(max = 20)
                         String idNumber,
                         @Size(max = 100)
                         String previousSchool,
                         @NotNull(message = "Es obligatorio indicar si tiene adecuaciones")
                         Boolean hasAccommodations) implements Serializable {
}