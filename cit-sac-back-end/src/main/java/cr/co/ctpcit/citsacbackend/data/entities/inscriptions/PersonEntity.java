package cr.co.ctpcit.citsacbackend.data.entities.inscriptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Generated;

import java.util.Objects;

import static org.hibernate.generator.EventType.INSERT;
import static org.hibernate.generator.EventType.UPDATE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_persons")
public class PersonEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "person_id", columnDefinition = "int UNSIGNED")
  private Long id;

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
  @Column(name = "id_type", nullable = false, columnDefinition = "enum('CC','DI','PA')")
  @Enumerated(EnumType.STRING)
  private IdType idType;

  @Size(max = 32)
  @NotNull
  @Column(name = "id_number", nullable = false, length = 32)
  private String idNumber;

  @Size(max = 64)
  @Generated(event = {INSERT, UPDATE})
  @Column(name = "full_surname", length = 64,
      columnDefinition = "AS CONCAT(`first_surname`,_utf8mb4' ',`second_surname`)")
  private String fullSurname;

  @JsonIgnore
  @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
  private StudentEntity student;

  @JsonIgnore
  @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
  private ParentEntity parent;

  public void addParent(ParentEntity parent) {
    this.parent = parent;
    parent.setPerson(this);
  }

  public void addStudent(StudentEntity student) {
    this.student = student;
    student.setPerson(this);
  }

  public void removeParent() {
    if (parent != null) {
      parent.setPerson(null);
      parent = null;
    }
  }

  public void removeStudent() {
    if (student != null) {
      student.setPerson(null);
      student = null;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    PersonEntity that = (PersonEntity) o;
    return id.equals(that.id) && firstName.equals(that.firstName) && firstSurname.equals(
        that.firstSurname) && idType == that.idType && idNumber.equals(that.idNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
