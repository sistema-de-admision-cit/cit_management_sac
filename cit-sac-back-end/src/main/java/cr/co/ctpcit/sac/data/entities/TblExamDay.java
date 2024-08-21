package cr.co.ctpcit.sac.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_examdays")
public class TblExamDay {
    @EmbeddedId
    private TblExamDayId id;

    @MapsId("examPeriodId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exam_period_id", nullable = false)
    private TblExamPeriod examPeriod;

    @Lob
    @Column(name = "exam_day", nullable = false)
    private String examDay;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

}