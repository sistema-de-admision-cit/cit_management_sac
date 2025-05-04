package cr.co.ctpcit.citsacbackend.logic.mappers.reports;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.AddressEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.GradeType;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.ReportDataDto;
import cr.co.ctpcit.citsacbackend.logic.dto.reports.ReportRequestDto;
import org.springframework.stereotype.Component;

import static cr.co.ctpcit.citsacbackend.data.enums.GradeType.*;

/**
 * Component responsible for mapping {@link EnrollmentEntity} data into {@link ReportDataDto}
 * based on the report type specified in {@link ReportRequestDto}.
 */

@Component
public class ReportMapper {

    /**
     * Maps the data from a given {@link EnrollmentEntity} to a {@link ReportDataDto},
     * including details such as student ID, name, enrollment date, and a category value
     * depending on the selected report type.
     *
     * @param inscripcion The {@link EnrollmentEntity} containing the enrollment data.
     * @param request The {@link ReportRequestDto} specifying the type of report and filters.
     * @return A {@link ReportDataDto} containing the mapped data for reporting purposes.
     */

    public static ReportDataDto mapDataReport(EnrollmentEntity inscripcion, ReportRequestDto request) {
        ReportDataDto dto = new ReportDataDto();
        StudentEntity estudiante = inscripcion.getStudent();
        PersonEntity persona = estudiante.getStudentPerson();

        dto.setStudentId(persona.getIdNumber());
        dto.setFirstName(persona.getFirstName());
        dto.setFullSurname(persona.getFullSurname());
        dto.setEnrollmentDate(inscripcion.getEnrollmentDate());

        switch (request.getReportType()) {
            case KNOWN_THROUGH:
                dto.setReportCategory(inscripcion.getKnownThrough().name());
                break;
            case GRADE_TO_ENROLL:
                dto.setReportCategory(inscripcion.getGradeToEnroll().name());
                break;
            case PROCESS_STATUS:
                dto.setReportCategory(inscripcion.getStatus().name());
                break;
            case PROVINCE:
                String provincia = inscripcion.getStudent().getParents().stream()
                        .flatMap(ps -> ps.getParent().getAddresses().stream())
                        .findFirst()
                        .map(AddressEntity::getProvince)
                        .orElse("DESCONOCIDA");
                dto.setReportCategory(provincia);
                break;
            case GRADES:
                dto.setEnglishGrade(getEnglishGrade(inscripcion));
                dto.setAcademicGrade(getAcademicGrade(inscripcion));
                dto.setPreviousGrade(estudiante.getPreviousGrades() != null ?
                        estudiante.getPreviousGrades().toString() : "N/A");

                if (request.getGradeTypeFilter() != null) {
                    switch (request.getGradeTypeFilter()) {
                        case ENGLISH:
                            dto.setReportCategory(getEnglishGrade(inscripcion));
                            break;
                        case ACADEMIC:
                            dto.setReportCategory(getAcademicGrade(inscripcion));
                            break;
                        case PREVIOUS:
                            dto.setReportCategory(estudiante.getPreviousGrades() != null ?
                                    estudiante.getPreviousGrades().toString() : "N/A");
                            break;
                    }
                }
                break;
        }

        return dto;
    }


    /**
     * Retrieves the English proficiency level from a student's enrollment record.
     *
     * Examines all exams associated with the enrollment and returns the first English exam level found.
     * The level is represented as standard CEFR levels (A1, A2, B1, B2, C1, C2).
     *
     * @param enrollment the enrollment entity containing exam information
     * @return the English proficiency level as a String, or "N/A" if no English exam is found
     */
    private static String getEnglishGrade(EnrollmentEntity enrollment) {
        return enrollment.getExams().stream()
                .filter(e -> e.getEnglishExam() != null)
                .findFirst()
                .map(e -> e.getEnglishExam().getLevel().name())
                .orElse("N/A");
    }

    /**
     * Retrieves the academic grade from a student's enrollment record.
     *
     * Examines all exams associated with the enrollment and returns the first academic exam grade found.
     * The grade is returned as a string representation of a decimal number (e.g., "85.50").
     *
     * @param enrollment the enrollment entity containing exam information
     * @return the academic grade as a String, or "N/A" if no academic exam is found
     */
    private static String getAcademicGrade(EnrollmentEntity enrollment) {
        return enrollment.getExams().stream()
                .filter(e -> e.getAcademicExam() != null)
                .findFirst()
                .map(e -> e.getAcademicExam().getGrade().toString())
                .orElse("N/A");
    }


}
