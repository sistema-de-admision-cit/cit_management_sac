package cr.co.ctpcit.citsacbackend.data.entities.inscription;

import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import cr.co.ctpcit.citsacbackend.data.enums.Relationship;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Validated
@Table(name = "tbl_parentsguardians")
public class ParentsGuardianEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parent_guardian_id", nullable = false)
    private Integer id;

    @Size(max = 32)
    @NotNull
    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "first_name", nullable = false, length = 32)
    private String firstName;

    @Size(max = 32)
    @NotNull
    @NotBlank(message = "El primer apellido es obligatorio")
    @Column(name = "first_surname", nullable = false, length = 32)
    private String firstSurname;

    @Size(max = 32)
    @Column(name = "second_surname", length = 32)
    private String secondSurname;

    @NotNull
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "El tipo de identificación es obligatorio")
    @Column(name = "id_type", nullable = false)
    private IdType idType;

    @Size(max = 20)
    @NotNull
    @NotBlank(message = "El número de identificación es obligatorio")
    @Column(name = "id_number", nullable = false, length = 20)
    private String idNumber;

    @Size(max = 20)
    @NotNull
    @NotBlank(message = "El número de teléfono es obligatorio")
    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Size(max = 100)
    @NotNull
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Size(max = 100)
    @NotNull
    @NotBlank(message = "La dirección de casa es obligatoria")
    @Column(name = "home_address", nullable = false, length = 100)
    private String homeAddress;

    @NotNull
    @Enumerated(EnumType.STRING)
    @NotBlank(message = "La relación con el estudiante es obligatoria")
    @Column(name = "relationship", nullable = false)
    private Relationship relationship;

    @OneToMany(mappedBy = "parentGuardian")
    @ToString.Exclude
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