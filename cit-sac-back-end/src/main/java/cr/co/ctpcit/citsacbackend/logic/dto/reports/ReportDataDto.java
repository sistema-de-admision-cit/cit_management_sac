package cr.co.ctpcit.citsacbackend.logic.dto.reports;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;


/**
 * Data Transfer Object (DTO) used to represent the data required in a report
 * This DTO contains information about the student and a category field
 * that varies depending on the selected report type.
 */

@Setter
@Getter
public class ReportDataDto {
    /**
     * Identification number of the student.
     */
    private String studentId;

    /**
     * First name of the student.
     */
    private String firstName;

    /**
     * Full surname (including both last names) of the student.
     */
    private String fullSurname;

    /**
     * Date and time when the student was enrolled.
     */
    private LocalDateTime enrollmentDate;

    /**
     * Report-specific category field. Its value depends on the selected report type:
     * for example, known through method, grade to enroll, process status, or province.
     */
    private String reportCategory;


    /**
     * The English proficiency level of the student.
     * Possible values are A1, A2, B1, B2, C1, C2 or N/A if not available.
     * This field is populated when the report type is GRADES and grade type is ENGLISH or ALL.
     */
    private String englishGrade;

    /**
     * The academic grade of the student as a decimal number.
     * Represents the score obtained in academic exams.
     * Format is typically with 2 decimal places (e.g., "85.50").
     * Value is "N/A" if not available.
     * This field is populated when the report type is GRADES and grade type is ACADEMIC or ALL.
     */
    private String academicGrade;

    /**
     * The previous academic grades of the student from their prior school.
     * Represents the historical academic performance.
     * Format is typically with 2 decimal places (e.g., "78.25").
     * Value is "N/A" if not available.
     * This field is populated when the report type is GRADES and grade type is PREVIOUS or ALL.
     */
    private String previousGrade;
}
