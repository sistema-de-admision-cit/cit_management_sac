package cr.co.ctpcit.citsacbackend.data.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_documents")
public class DocumentEntity {
    @EmbeddedId
    private DocumentEntityId id;

    @MapsId("enrollmentId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false, referencedColumnName = "enrollment_id")
    private EnrollmentEntity enrollment;

    @NotNull
    @Lob
    @Column(name = "document_type", nullable = false)
    private String documentType;

    @Size(max = 255)
    @NotNull
    @Column(name = "document_url", nullable = false)
    private String documentUrl;

}