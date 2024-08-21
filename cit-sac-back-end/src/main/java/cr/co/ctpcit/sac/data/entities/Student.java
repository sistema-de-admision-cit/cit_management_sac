package cr.co.ctpcit.sac.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_students")
public class Student {
    @EmbeddedId
    private StudentId id;

    @MapsId("parentGuardianId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parent_guardian_id", nullable = false)
    private ParentsGuardian parentGuardian;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Lob
    @Column(name = "id_type", nullable = false)
    private String idType;

    @Column(name = "id_number", nullable = false, length = 20)
    private String idNumber;

    @Column(name = "previous_school", length = 100)
    private String previousSchool;

    @Column(name = "has_accommodations", nullable = false)
    private Boolean hasAccommodations = false;

}