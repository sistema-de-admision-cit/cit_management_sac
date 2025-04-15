package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing the details of a student.
 * Contains the student's personal information, birth date, previous school details,
 * accommodations, previous grades, and associated parents.
 */
@Builder
public record StudentDto(Long id,
                         PersonDto person,
                         @NotNull LocalDate birthDate,
                         @Size(max = 128) String previousSchool,
                         @NotNull Boolean hasAccommodations,
                         BigDecimal previousGrades,
                         List<ParentDto> parents) implements Serializable {
}

