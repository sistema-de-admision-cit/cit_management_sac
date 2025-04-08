package cr.co.ctpcit.citsacbackend.data.entities.inscriptions;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_parents_students")
public class ParentsStudentsEntity implements Serializable {
  @Id
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JsonBackReference
  @JoinColumn(name = "student_id", nullable = false)
  private StudentEntity student;

  @Id
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "parent_id", nullable = false)
  private ParentEntity parent;

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ParentsStudentsEntity that = (ParentsStudentsEntity) o;
    return student.equals(that.student) && parent.equals(that.parent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(student, parent);
  }

}
