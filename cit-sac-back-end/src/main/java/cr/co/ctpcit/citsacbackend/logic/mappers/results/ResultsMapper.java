package cr.co.ctpcit.citsacbackend.logic.mappers.results;

import cr.co.ctpcit.citsacbackend.data.entities.exams.AcademicExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.EnglishExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import cr.co.ctpcit.citsacbackend.data.utils.ResultUtils;
import cr.co.ctpcit.citsacbackend.logic.dto.results.ResultDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ResultsMapper {


    public static ResultDTO mapToExamResultDTO(
            EnrollmentEntity enrollment,
            BigDecimal englishWeight,
            BigDecimal academicWeight,
            BigDecimal prevGradesWeight) {

        PersonEntity person = enrollment.getStudent().getStudentPerson();
        EnglishExamEntity englishExam = ResultUtils.getEnglishExam(enrollment);
        AcademicExamEntity academicExam = ResultUtils.getAcademicExam(enrollment);

        BigDecimal englishScore = ResultUtils.convertEnglishLevelToScore(englishExam.getLevel());
        BigDecimal academicScore = academicExam.getGrade();
        BigDecimal prevGradesScore = enrollment.getStudent().getPreviousGrades();

        BigDecimal finalGrade = englishScore.multiply(englishWeight)
                .add(academicScore.multiply(academicWeight))
                .add(prevGradesScore.multiply(prevGradesWeight))
                .setScale(2, RoundingMode.HALF_UP);

        return new ResultDTO(
                person.getIdNumber(),
                person.getFirstName(),
                person.getFirstSurname(),
                person.getSecondSurname(),
                enrollment.getExamDate(),
                enrollment.getGradeToEnroll(),
                finalGrade,
                enrollment.getStatus()
        );
    }
}
