package cr.co.ctpcit.citsacbackend.data.entities.exams.dai;
import cr.co.ctpcit.citsacbackend.data.entities.inscription.EnrollmentEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_daigrades")
public class DaiGradesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "daigrades_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false)
    @ToString.Exclude
    private EnrollmentEntity enrollmentId;
}
