package cr.co.ctpcit.citsacbackend.data.entities.inscription;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

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
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "student_id")
  @ToString.Exclude
  private StudentEntity student;

  @MapsId("parentGuardianId")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parentguardian_id")
  @ToString.Exclude
  private ParentsGuardianEntity parentGuardian;

  public ParentGuardianStudentEntity(StudentEntity student, ParentsGuardianEntity parentGuardian) {
    this.student = student;
    this.parentGuardian = parentGuardian;
    this.id = new ParentGuardianStudentEntityId(student.getId(), parentGuardian.getId());
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null)
      return false;
    Class<?> oEffectiveClass = o instanceof HibernateProxy ?
        ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() :
        o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy ?
        ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() :
        this.getClass();
    if (thisEffectiveClass != oEffectiveClass)
      return false;
    ParentGuardianStudentEntity that = (ParentGuardianStudentEntity) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public final int hashCode() {
    return Objects.hash(student, parentGuardian);
  }
}
