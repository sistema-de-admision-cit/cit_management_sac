package cr.co.ctpcit.citsacbackend.data.entities.configs;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_exam_periods")
public class ExamPeriodEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "exam_period_id", columnDefinition = "INT UNSIGNED")
  private Long id;

  @NotNull
  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @NotNull
  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  @OneToMany(mappedBy = "examPeriod", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<ExamDayEntity> examDays = new ArrayList<>();

  public void addExamDay(ExamDayEntity examDay) {
    examDays.add(examDay);
    examDay.setExamPeriod(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ExamPeriodEntity that = (ExamPeriodEntity) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}
