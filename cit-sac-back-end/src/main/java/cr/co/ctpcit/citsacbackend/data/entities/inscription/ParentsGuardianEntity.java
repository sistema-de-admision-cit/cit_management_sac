package cr.co.ctpcit.citsacbackend.data.entities.inscription;

import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import cr.co.ctpcit.citsacbackend.data.enums.Relationship;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_parentsguardians")
public class ParentsGuardianEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parent_guardian_id", nullable = false)
    private Integer id;

    @Size(max = 32)
    @NotNull
    @Column(name = "first_name", nullable = false, length = 32)
    private String firstName;

    @Size(max = 32)
    @NotNull
    @Column(name = "first_surname", nullable = false, length = 32)
    private String firstSurname;

    @Size(max = 32)
    @Column(name = "second_surname", length = 32)
    private String secondSurname;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "id_type", nullable = false)
    private IdType idType;

    @Size(max = 20)
    @NotNull
    @Column(name = "id_number", nullable = false, length = 20)
    private String idNumber;

    @Size(max = 20)
    @NotNull
    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Size(max = 100)
    @NotNull
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Size(max = 100)
    @NotNull
    @Column(name = "home_address", nullable = false, length = 100)
    private String homeAddress;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "relationship", nullable = false)
    private Relationship relationship;

    @OneToMany(mappedBy = "parentGuardian")
    private List<ParentGuardianStudentEntity> students;

    public void addStudent(ParentGuardianStudentEntity parentGuardianStudentEntity) {
        if (students == null) students = List.of();
        if (parentGuardianStudentEntity == null) return;
        if (students.contains(parentGuardianStudentEntity)) return;

        students.add(parentGuardianStudentEntity);
        parentGuardianStudentEntity.setParentGuardian(this);
    }

    public void removeStudent(ParentGuardianStudentEntity parentGuardianStudentEntity) {
        if (students == null) students = List.of();
        if (parentGuardianStudentEntity == null) return;

        students.remove(parentGuardianStudentEntity);
        parentGuardianStudentEntity.setParentGuardian(null);
    }
}