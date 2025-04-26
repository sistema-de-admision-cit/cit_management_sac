package cr.co.ctpcit.citsacbackend.logic.dto.results;

import cr.co.ctpcit.citsacbackend.data.enums.EnglishLevel;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.data.enums.Recommendation;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StudentResultsDetailsDTO {

    private String fullName;
    private String idNumber;
    private Grades academicGrade;
    private BigDecimal prevGrades;
    private EnglishLevel englishLevel;
    private BigDecimal academicGradeScore;
    private BigDecimal finalGrade;
    private Recommendation daiRecommendation;
    private String daiComment;
    private ProcessStatus currentStatus;

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
