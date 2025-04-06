package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.AcademicExamDetailsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.DaiExamDetailsDto;
import lombok.Builder;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity} with exams
 * @param id
 * @param person
 * @param academicExams
 * @param daiExams
 */
@Builder
public record StudentExamsDto(Long id, PersonDto person, List<AcademicExamDetailsDto> academicExams,
                              List<DaiExamDetailsDto> daiExams) implements Serializable {
}
