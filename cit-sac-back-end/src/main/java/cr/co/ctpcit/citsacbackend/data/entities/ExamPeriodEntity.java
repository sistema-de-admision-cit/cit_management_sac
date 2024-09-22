package cr.co.ctpcit.citsacbackend.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_examperiods")
public class ExamPeriodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int examPeriodId;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    public void setExamPeriodId(int examPeriodId) {
        this.examPeriodId = examPeriodId;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}