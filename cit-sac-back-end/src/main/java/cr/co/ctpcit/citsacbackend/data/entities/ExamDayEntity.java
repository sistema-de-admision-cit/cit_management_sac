package cr.co.ctpcit.citsacbackend.data.entities;

import cr.co.ctpcit.citsacbackend.data.enums.WeekDays;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_examdays")
public class ExamDayEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int examDayId;

  @ManyToOne
  @JoinColumn(name = "exam_period_id", nullable = false)
  private ExamPeriodEntity examPeriod;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private WeekDays examDay;

  @Column(nullable = false)
  private String startTime;

  public int getExamDayId() {
    return examDayId;
  }

  public void setExamDayId(int examDayId) {
    this.examDayId = examDayId;
  }

  public ExamPeriodEntity getExamPeriod() {
    return examPeriod;
  }

  public void setExamPeriod(ExamPeriodEntity examPeriod) {
    this.examPeriod = examPeriod;
  }

  public WeekDays getExamDay() {
    return examDay;
  }

  public void setExamDay(WeekDays examDay) {
    this.examDay = examDay;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }
}
