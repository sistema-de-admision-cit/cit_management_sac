package cr.co.ctpcit.citsacbackend.data.entities;

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
    private cr.co.ctpcit.citsacbackend.ExamPeriodEntity examPeriod;

    @NotNull
    @Lob
    @Column(name = "exam_day", nullable = false)
    private String examDay;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

}