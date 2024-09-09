package cr.co.ctpcit.citsacbackend.data.entities.inscription;

import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_students")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", nullable = false)
    private Integer id;

    @OneToMany(mappedBy = "student")
    private List<EnrollmentEntity> enrollments;

    @OneToMany(mappedBy = "student")
    private List<ParentGuardianStudentEntity> parents;

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
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "id_type", nullable = false)
    private IdType idType;

    @Size(max = 20)
    @NotNull
    @Column(name = "id_number", nullable = false, length = 20)
    private String idNumber;

    @Size(max = 100)
    @Column(name = "previous_school", length = 100)
    private String previousSchool;

    @NotNull
    @Column(name = "has_accommodations", nullable = false)
    private Boolean hasAccommodations = false;

    public void addEnrollment(EnrollmentEntity enrollmentEntity) {
        if (enrollments == null) enrollments = List.of();
        if (enrollmentEntity == null) return;
        if (enrollments.contains(enrollmentEntity)) return;

        enrollments.add(enrollmentEntity);
        enrollmentEntity.setStudent(this);
    }

    public void addParentGuardian(ParentGuardianStudentEntity parentGuardianStudentEntity) {
        if (parents == null) parents = List.of();
        if (parentGuardianStudentEntity == null) return;
        if (parents.contains(parentGuardianStudentEntity)) return;

        parents.add(parentGuardianStudentEntity);
        parentGuardianStudentEntity.setStudent(this);
    }
}