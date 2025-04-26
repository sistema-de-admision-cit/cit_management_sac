package cr.co.ctpcit.citsacbackend.logic.dto.results;

import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import lombok.Getter;
import lombok.Setter;
import cr.co.ctpcit.citsacbackend.data.enums.Grades;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ResultDTO {

    private String idNumber;
    private String firstName;
    private String firstSurname;
    private String secondSurname;
    private LocalDate examDate;
    private Grades gradeToEnroll;
    private BigDecimal finalGrade;
    private ProcessStatus status;

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
