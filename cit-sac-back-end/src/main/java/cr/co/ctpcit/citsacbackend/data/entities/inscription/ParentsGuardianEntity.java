package cr.co.ctpcit.citsacbackend.data.entities.inscription;

import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import cr.co.ctpcit.citsacbackend.data.enums.Relationship;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
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
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Long id;

    @Size(max = 32)
    @NotNull(message = "El nombre es obligatorio")
    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "first_name", nullable = false, length = 32)
    private String firstName;

    @Size(max = 32)
    @NotNull(message = "El primer apellido es obligatorio")
    @NotBlank(message = "El primer apellido es obligatorio")
    @Column(name = "first_surname", nullable = false, length = 32)
    private String firstSurname;

    @Size(max = 32)
    @Column(name = "second_surname", length = 32)
    private String secondSurname;

    @NotNull(message = "El tipo de identificación es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "id_type", nullable = false)
    private IdType idType;

    @Size(max = 20)
    @NotNull(message = "El número de identificación es obligatorio")
    @NotBlank(message = "El número de identificación es obligatorio")
    @Column(name = "id_number", nullable = false, length = 20)
    private String idNumber;

    @Size(max = 20)
    @NotNull(message = "El número de teléfono es obligatorio")
    @NotBlank(message = "El número de teléfono es obligatorio")
    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Size(max = 100)
    @NotNull(message = "El correo electrónico es obligatorio")
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @NotNull(message = "La relación con el estudiante es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(name = "relationship", nullable = false)
    private Relationship relationship;

    @NotEmpty(message = "La lista de direcciones no puede estar vacía")
    @OneToMany(mappedBy = "parentGuardian", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<AddressEntity> addresses;

    @OneToMany(mappedBy = "parentGuardian", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ParentGuardianStudentEntity> students;

    public void addStudent(ParentGuardianStudentEntity parentGuardianStudentEntity) {
        if (students == null) students = List.of();
        if (parentGuardianStudentEntity == null) return;
        if (students.contains(parentGuardianStudentEntity)) return;

        students.add(parentGuardianStudentEntity);
        parentGuardianStudentEntity.setParentGuardian(this);
    }

    public void addAddress(AddressEntity addressEntity) {
        if (addresses == null) addresses = List.of();
        if (addressEntity == null) return;
        if (addresses.contains(addressEntity)) return;

        addresses.add(addressEntity);
        addressEntity.setParentGuardian(this);
    }

    public void removeAddress(AddressEntity addressEntity) {
        if (addresses == null) return;
        if (addressEntity == null) return;
        if (!addresses.contains(addressEntity)) return;

        addresses.remove(addressEntity);
        addressEntity.setParentGuardian(null);
    }
}