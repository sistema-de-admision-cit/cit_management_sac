package cr.co.ctpcit.citsacbackend.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_daiinterview")
public class DaiInterviewEntity {
    @EmbeddedId
    private DaiInterviewEntityId id;

    @MapsId("daigradesId")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "daigrades_id", nullable = false, referencedColumnName = "daigrades_id")
    private DaiGradeEntity daigrades;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "interview_date")
    private Instant interviewDate;

    @NotNull
    @Column(name = "grade", nullable = false, precision = 5, scale = 2)
    private BigDecimal grade;

}