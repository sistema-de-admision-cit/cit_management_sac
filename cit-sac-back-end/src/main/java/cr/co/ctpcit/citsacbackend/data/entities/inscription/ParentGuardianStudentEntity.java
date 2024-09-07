package cr.co.ctpcit.citsacbackend.data.entities.inscription;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_parentguardianstudents")
public class ParentGuardianStudentEntity {
    @EmbeddedId
    private ParentGuardianStudentEntityId id;

    @MapsId("studentId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    @ToString.Exclude
    private StudentEntity student;

    @MapsId("parentGuardianId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parentguardian_id", nullable = false)
    @ToString.Exclude
    private ParentsGuardianEntity parentGuardian;

}