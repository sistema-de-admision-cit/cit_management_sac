package cr.co.ctpcit.citsacbackend.data.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_daigrades")
public class DaiGradeEntity {
    @EmbeddedId
    private DaiGradeEntityId id;

    @MapsId("enrollmentId")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false, referencedColumnName = "enrollment_id")
    private EnrollmentEntity enrollment;

}