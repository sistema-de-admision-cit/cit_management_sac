package cr.co.ctpcit.citsacbackend.logic.dto.results;

import cr.co.ctpcit.citsacbackend.data.enums.EnglishLevel;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.data.enums.Recommendation;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) that contains detailed exam results and enrollment information for a student.
 */
@Getter
@Setter
public class StudentResultsDetailsDTO {

    /**
     * The student's full name.
     */
    private String fullName;

    /**
     * The student's identification number.
     */
    private String idNumber;

    /**
     * The grade level the student is eligible to enroll in.
     */
    private Grades academicGrade;

    /**
     * The student's previous academic grades.
     */
    private BigDecimal prevGrades;

    /**
     * The student's English proficiency level.
     */
    private EnglishLevel englishLevel;

    /**
     * The student's academic exam score.
     */
    private BigDecimal academicGradeScore;

    /**
     * The student's final calculated grade.
     */
    private BigDecimal finalGrade;

    /**
     * The recommendation provided by the DAI exam (if applicable).
     */
    private Recommendation daiRecommendation;

    /**
     * Additional comments provided by the DAI exam (if applicable).
     */
    private String daiComment;

    /**
     * The student's current enrollment status.
     */
    private ProcessStatus currentStatus;

    /**
     * Constructs a new {@link StudentResultsDetailsDTO} with the specified detailed information.
     *
     * @param fullName the student's full name
     * @param idNumber the student's identification number
     * @param academicGrade the grade the student is eligible to enroll in
     * @param prevGrades the student's previous academic grades
     * @param englishLevel the student's English proficiency level
     * @param academicGradeScore the student's academic exam score
     * @param finalGrade the student's final calculated grade
     * @param daiRecommendation the DAI exam recommendation, if available
     * @param daiComment the DAI exam comment, if available
     * @param currentStatus the student's current enrollment status
     */
    public StudentResultsDetailsDTO(String fullName, String idNumber, Grades academicGrade,
                                    BigDecimal prevGrades, EnglishLevel englishLevel,
                                    BigDecimal academicGradeScore, BigDecimal finalGrade,
                                    Recommendation daiRecommendation, String daiComment,
                                    ProcessStatus currentStatus) {
        this.fullName = fullName;
        this.idNumber = idNumber;
        this.academicGrade = academicGrade;
        this.prevGrades = prevGrades;
        this.englishLevel = englishLevel;
        this.academicGradeScore = academicGradeScore;
        this.finalGrade = finalGrade;
        this.daiRecommendation = daiRecommendation;
        this.daiComment = daiComment;
        this.currentStatus = currentStatus;
    }

}

