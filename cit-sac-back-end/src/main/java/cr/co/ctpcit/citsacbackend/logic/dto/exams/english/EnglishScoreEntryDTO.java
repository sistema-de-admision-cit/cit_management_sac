package cr.co.ctpcit.citsacbackend.logic.dto.exams.english;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;

/* Tentative version of the DTO for the English exam score entry */
/* I DO NOT KNOW IF THIS IS THE FINAL VERSION */


/**
 * DTO for updating English exam scores.
 *
 * @param trackTestExamId   Track test exam ID - sync this with our database.
 * @param applicantFullName Applicant full name - to look for the applicant in the database.
 * @param score             Score of the exam.
 * @param examDate          Date of the exam - to look for the enrollment in the database.
 */
public record EnglishScoreEntryDTO(@NotNull Long trackTestExamId, @NotNull String applicantFullName,
                                   @NotNull @Positive Double score, @NotNull Instant examDate) {

    /*
    Expected flow:
      1. The user sends a request to update the score of an English exam.
      2. The user sends the track test exam ID, the applicant full name, the score, and the exam date.
      3. The system looks for enrollments on that exam date.
      4. The system looks in these enrollments for the applicant with the full name.
      5. The system updates the score of the applicant.
      5.1 While updating the score, the system saves logs in the database `tbl_score_logs`.
      6. The system sends the logs for this operation to the user.
    */
}
