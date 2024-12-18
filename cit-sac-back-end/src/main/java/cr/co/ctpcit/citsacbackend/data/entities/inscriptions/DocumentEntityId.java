package cr.co.ctpcit.citsacbackend.data.entities.inscriptions;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class DocumentEntityId implements Serializable {
  @Column(name = "document_id", columnDefinition = "int UNSIGNED")
  private Long documentId;

  @Column(name = "enrollment_id", columnDefinition = "int UNSIGNED")
  private Long enrollmentId;

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
      return false;
    DocumentEntityId entity = (DocumentEntityId) o;
    return Objects.equals(this.enrollmentId, entity.enrollmentId) && Objects.equals(this.documentId,
        entity.documentId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(enrollmentId, documentId);
  }

}
