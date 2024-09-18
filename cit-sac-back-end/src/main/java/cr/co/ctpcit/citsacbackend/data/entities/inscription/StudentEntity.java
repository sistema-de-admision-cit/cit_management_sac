package cr.co.ctpcit.citsacbackend.data.entities.inscription;

import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.NaturalId;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Validated
@Table(name = "tbl_students")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", nullable = false)
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

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @NotNull(message = "El tipo de identificación es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "id_type", nullable = false)
    private IdType idType;

    @Size(max = 20)
    @NotNull(message = "El número de identificación es obligatorio")
    @NotBlank(message = "El número de identificación es obligatorio")
    @Column(name = "id_number", nullable = false, length = 20)
    private String idNumber;

    @Size(max = 100)
    @Column(name = "previous_school", length = 100)
    private String previousSchool;

    @NotNull(message = "Es obligatorio indicar si tiene adecuaciones")
    @Column(name = "has_accommodations", nullable = false)
    @ColumnDefault("false")
    private Boolean hasAccommodations;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<EnrollmentEntity> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<ParentGuardianStudentEntity> parents = new ArrayList<>();

    public void addEnrollment(EnrollmentEntity enrollmentEntity) {
        if (enrollments == null) enrollments = new ArrayList<>();
        if (enrollmentEntity == null) return;
        if (enrollments.contains(enrollmentEntity)) return;

        enrollments.add(enrollmentEntity);
        enrollmentEntity.setStudent(this);
    }

    public void addParentGuardian(ParentGuardianStudentEntity parentGuardianStudentEntity) {
        if (parents == null) parents = new ArrayList<>();
        if (parentGuardianStudentEntity == null) return;
        if (parents.contains(parentGuardianStudentEntity)) return;

        parents.add(parentGuardianStudentEntity);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        StudentEntity student = (StudentEntity) o;
        return getId() != null && Objects.equals(getId(), student.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}