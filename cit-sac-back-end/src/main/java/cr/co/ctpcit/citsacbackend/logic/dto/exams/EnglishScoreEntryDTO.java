package cr.co.ctpcit.citsacbackend.logic.dto.exams;

import cr.co.ctpcit.citsacbackend.data.enums.EnglishLevel;
import jakarta.validation.constraints.NotNull;

/**
 * This class represents the data transfer object for the English score entry.
 *
 * @param id        track test exam ID
 * @param names     applicant full name
 * @param lastNames applicant last names (first and second)
 * @param lastTest  exam date (hopefully)
 * @param level     English level (A1, A2, B1, B2, C1, C2)
 * @param core      score in percentage
 */
public record EnglishScoreEntryDTO(@NotNull Long id, @NotNull String names,
                                   @NotNull String lastNames, @NotNull String lastTest,
                                   @NotNull EnglishLevel level, @NotNull String core) {

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

  /*
  Assumptions:
    1. The names and last names are the ones registered in the system.
      - expected input: names = "Juan Carlos", lastNames = "Pérez Sánchez"
      - expected database columns: first_name = "Juan Carlos", first_surname = "Pérez", second_surname = "Sánchez"
      - ignoring accents, special characters and case sensitivity in the comparison
      - González = gonzalez
    2. The last date is the date of the exam.
      - expected input: lastTest = "2024-09-11"
      - expected database column: tbl_enrollments.exam_date = "2024-09-11"
    3. The ID is unique.
   */
}
