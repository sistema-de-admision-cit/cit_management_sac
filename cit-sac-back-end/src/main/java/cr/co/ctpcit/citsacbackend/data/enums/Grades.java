package cr.co.ctpcit.citsacbackend.data.enums;

import lombok.Getter;

/**
 * This ENUM match with the DataSource GradeToEnroll enum in tbl_enrollments,
 * and it is used to determine the grade that the student is going to enroll.
 * Every line in this enum match the grades that uses the same test in the process,
 * so there is a test for 1-3 grades, another for 4-6 grades, and so on.
 */
@Getter
public enum Grades {
    FIRST("1"), SECOND("2"), THIRD("3"),
    FORTH("4"), FIFTH("5"), SIXTH("6"),
    SEVENTH("7"),
    EIGHTH("8"),
    NINTH("9"),
    TENTH("10"),;

    private final String grade;

    Grades(String grade) {
        this.grade = grade;
    }

    public static Grades fromGrade(String grade) {
        if(grade == null) return null;
        for (Grades g : Grades.values()) {
            if (g.getGrade().equals(grade)) {
                return g;
            }
        }
        return null;
    }

}