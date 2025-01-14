package cr.co.ctpcit.citsacbackend.data.entities.inscriptions;

import com.fasterxml.jackson.annotation.JsonBackReference;
import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_documents")
public class DocumentEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "document_id", columnDefinition = "INT UNSIGNED")
  private Long id;

  @NotNull
  @JsonBackReference
  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
  @JoinColumn(name = "enrollment_id", nullable = false, columnDefinition = "INT UNSIGNED")
  private EnrollmentEntity enrollment;

  @Size(max = 64)
  @NotNull
  @Column(name = "document_name", nullable = false, length = 64)
  private String documentName;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(name = "document_type", nullable = false, columnDefinition = "enum('AC', 'OT')")
  private DocType docType;

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

    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
