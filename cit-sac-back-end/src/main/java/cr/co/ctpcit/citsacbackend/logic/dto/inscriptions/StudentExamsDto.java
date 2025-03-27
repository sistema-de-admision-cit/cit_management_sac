package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.logic.dto.exams.AcademicExamDetailsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.DaiExamDetailsDto;

import java.io.Serializable;
import java.util.List;

public record StudentExamsDto(Long id, PersonDto person, List<AcademicExamDetailsDto> academicExams,
                              List<DaiExamDetailsDto> daiExams) implements Serializable {
}
