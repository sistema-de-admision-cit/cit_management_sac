package cr.co.ctpcit.citsacbackend.data.entities.inscriptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import cr.co.ctpcit.citsacbackend.data.enums.Relationship;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_parents")
public class ParentEntity implements Serializable {
  @Id
  @Column(name = "parent_id", columnDefinition = "int UNSIGNED")
  private Long id;

  @MapsId
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "parent_id", nullable = false, columnDefinition = "int UNSIGNED")
  private PersonEntity parentPerson;

  @Size(max = 32)
  @NotNull
  @Column(name = "phone_number", nullable = false, length = 32)
  private String phoneNumber;

  @Size(max = 64)
  @NotNull
  @Column(name = "email", nullable = false, length = 64)
  private String email;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "relationship", nullable = false, columnDefinition = "enum('M','F','G')")
  private Relationship relationship;

  @Column(name = "dai_exam", columnDefinition = "json")
  @JdbcTypeCode(SqlTypes.JSON)
  @Builder.Default
  private Map<String, Object> daiExam = new HashMap<>();

  @JsonIgnore
  @Builder.Default
  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ParentsStudentsEntity> students = new ArrayList<>();

  @JsonManagedReference
  @Builder.Default
  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<AddressEntity> addresses = new ArrayList<>();

  public void addStudent(StudentEntity student) {
    ParentsStudentsEntity parentsStudentsEntity = new ParentsStudentsEntity(student, this);
    students.add(parentsStudentsEntity);
    student.getParents().add(parentsStudentsEntity);
  }

  public void removeStudent(StudentEntity student) {
    ParentsStudentsEntity parentsStudentsEntity = new ParentsStudentsEntity(student, this);
    student.getParents().remove(parentsStudentsEntity);
    students.remove(parentsStudentsEntity);
    parentsStudentsEntity.setParent(null);
    parentsStudentsEntity.setStudent(null);
  }

  public void addAddress(AddressEntity address) {
    addresses.add(address);
    address.setParent(this);
  }

  public void removeAddress(AddressEntity address) {
    addresses.remove(address);
    address.setParent(null);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ParentEntity that = (ParentEntity) o;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
