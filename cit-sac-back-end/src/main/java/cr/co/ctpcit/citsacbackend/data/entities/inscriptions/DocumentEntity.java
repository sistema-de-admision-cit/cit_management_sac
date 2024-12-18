package cr.co.ctpcit.citsacbackend.data.entities.inscriptions;

import com.fasterxml.jackson.annotation.JsonBackReference;
import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_documents")
public class DocumentEntity {
  @EmbeddedId
  private DocumentEntityId id;

  @MapsId("enrollmentId")
  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "enrollment_id", nullable = false, columnDefinition = "int UNSIGNED")
  private EnrollmentEntity enrollment;

  @Size(max = 32)
  @NotNull
  @Column(name = "document_name", nullable = false, length = 32)
  private String documentName;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "document_type", nullable = false)
  private DocType documentType;

  @Size(max = 128)
  @NotNull
  @Column(name = "document_url", nullable = false, length = 128)
  private String documentUrl;

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    DocumentEntity that = (DocumentEntity) o;
    return id.equals(that.id) && documentName.equals(
        that.documentName) && documentType == that.documentType && documentUrl.equals(
        that.documentUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
