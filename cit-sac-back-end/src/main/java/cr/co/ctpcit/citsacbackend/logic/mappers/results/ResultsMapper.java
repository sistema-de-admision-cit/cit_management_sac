package cr.co.ctpcit.citsacbackend.logic.mappers.results;

import cr.co.ctpcit.citsacbackend.data.entities.exams.AcademicExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.EnglishExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import cr.co.ctpcit.citsacbackend.data.utils.ResultUtils;
import cr.co.ctpcit.citsacbackend.logic.dto.results.ResultDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class responsible for mapping enrollment entities to result data transfer objects (DTOs).
 */

public class ResultsMapper {

    /**
     * Maps an {@link EnrollmentEntity} to a {@link ResultDTO} by calculating the final grade
     * based on English exam results, academic exam results, and previous grades, applying the corresponding weights.
     *
     * @param enrollment the enrollment entity containing student and exam data
     * @param academicWeight the weight to apply to the academic exam score
     * @param prevGradesWeight the weight to apply to the student's previous grades
     * @return a {@link ResultDTO} containing the mapped result information
     */

    public static ResultDTO mapToExamResultDTO(
            EnrollmentEntity enrollment,
            BigDecimal academicWeight,
            BigDecimal prevGradesWeight) {

        PersonEntity person = enrollment.getStudent().getStudentPerson();
        AcademicExamEntity academicExam = ResultUtils.getAcademicExam(enrollment);

        BigDecimal academicScore = academicExam.getGrade();
        BigDecimal prevGradesScore = enrollment.getStudent().getPreviousGrades();

        BigDecimal finalGrade = academicScore.multiply(academicWeight)
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
