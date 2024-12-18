package cr.co.ctpcit.citsacbackend.data.enums;

import lombok.Getter;

/**
 * This ENUM match with the DataSource GradeToEnroll enum in tbl_enrollments, and it is used to
 * determine the grade that the student is going to enroll. Every line in this enum match the grades
 * that uses the same test in the process, so there is a test for 1-3 grades, another for 4-6
 * grades, and so on.
 */
@Getter
public enum Grades {
  FIRST, SECOND, THIRD, FORTH, FIFTH, SIXTH, SEVENTH, EIGHTH, NINTH, TENTH

}
