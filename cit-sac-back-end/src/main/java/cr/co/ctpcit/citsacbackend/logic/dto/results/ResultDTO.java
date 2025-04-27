package cr.co.ctpcit.citsacbackend.logic.dto.results;

import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import lombok.Getter;
import lombok.Setter;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;

import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * Data Transfer Object (DTO) that represents the summarized exam result information for a student.
 */

@Getter
@Setter
public class ResultDTO {


    /**
     * The student's identification number.
     */
    private String idNumber;

    /**
     * The student's first name.
     */
    private String firstName;

    /**
     * The student's first surname.
     */
    private String firstSurname;

    /**
     * The student's second surname.
     */
    private String secondSurname;

    /**
     * The date when the exam was taken.
     */
    private LocalDate examDate;

    /**
     * The grade level to which the student is eligible to enroll based on their results.
     */
    private Grades gradeToEnroll;

    /**
     * The final calculated grade for the student.
     */
    private BigDecimal finalGrade;

    /**
     * The current enrollment status of the student.
     */
    private ProcessStatus status;

    /**
     * Constructs a new {@link ResultDTO} with the specified details.
     *
     * @param idNumber the student's identification number
     * @param firstName the student's first name
     * @param firstSurname the student's first surname
     * @param secondSurname the student's second surname
     * @param examDate the date of the exam
     * @param gradeToEnroll the grade the student is eligible to enroll in
     * @param finalGrade the student's final calculated grade
     * @param status the student's enrollment status
     */
    public ResultDTO(String idNumber, String firstName, String firstSurname,
                     String secondSurname, LocalDate examDate,
                     Grades gradeToEnroll, BigDecimal finalGrade,
                     ProcessStatus status) {
        this.idNumber = idNumber;
        this.firstName = firstName;
        this.firstSurname = firstSurname;
        this.secondSurname = secondSurname;
        this.examDate = examDate;
        this.gradeToEnroll = gradeToEnroll;
        this.finalGrade = finalGrade;
        this.status = status;
    }

}
