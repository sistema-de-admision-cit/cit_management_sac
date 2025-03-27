package cr.co.ctpcit.citsacbackend.data.entities.inscriptions;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_students")
public class StudentEntity implements Serializable {
  @Id
  @Column(name = "student_id", columnDefinition = "int UNSIGNED")
  private Long id;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id", nullable = false, columnDefinition = "int UNSIGNED")
  private PersonEntity studentPerson;

  @NotNull
  @Column(name = "birth_date", nullable = false)
  private LocalDate birthDate;

  @Size(max = 128)
  @Column(name = "previous_school", length = 128)
  private String previousSchool;

  @NotNull
  @Column(name = "has_accommodations", nullable = false)
  @Builder.Default
  private Boolean hasAccommodations = false;

  @JsonIgnore
  @Builder.Default
  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<EnrollmentEntity> enrollments = new ArrayList<>();

  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  @Builder.Default
  private List<ParentsStudentsEntity> parents = new ArrayList<>();

  public void addEnrollment(EnrollmentEntity enrollment) {
    enrollments.add(enrollment);
    enrollment.setStudent(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    StudentEntity that = (StudentEntity) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
