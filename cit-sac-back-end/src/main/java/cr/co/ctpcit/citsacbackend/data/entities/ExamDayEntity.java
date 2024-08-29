package cr.co.ctpcit.citsacbackend.data.entities;

import cr.co.ctpcit.citsacbackend.data.enums.WeekDays;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_examdays")
public class ExamDayEntity {
    @EmbeddedId
    private ExamDayEntityId id;

    @MapsId("examPeriodId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exam_period_id", nullable = false)
    private ExamPeriodEntity examPeriod;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "exam_day", nullable = false, columnDefinition = "ENUM('M','K','W','T','F','S','SS')")
    private WeekDays examDay;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

}