package cr.co.ctpcit.citsacbackend.data.entities.configs;

import cr.co.ctpcit.citsacbackend.data.enums.WeekDays;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_exam_days")
public class ExamDayEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "exam_day_id", columnDefinition = "INT UNSIGNED")
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
  @JoinColumn(name = "exam_period_id", nullable = false, columnDefinition = "INT UNSIGNED")
  private ExamPeriodEntity examPeriod;

  @NotNull
  @Column(name = "exam_day", nullable = false,
      columnDefinition = "ENUM('M', 'K', 'W', 'T', 'F', 'S', 'SS')")
  @Enumerated(EnumType.STRING)
  private WeekDays examDay;

  @NotNull
  @Column(name = "start_time", nullable = false)
  private LocalTime startTime;

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ExamDayEntity that = (ExamDayEntity) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
