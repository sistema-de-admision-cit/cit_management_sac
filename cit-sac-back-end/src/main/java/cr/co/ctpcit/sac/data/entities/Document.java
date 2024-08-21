package cr.co.ctpcit.sac.data.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tbl_documents")
public class Document {
    @EmbeddedId
    private DocumentId id;

    @MapsId("enrollmentId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false, referencedColumnName = "enrollment_id")
    private Enrollment enrollment;

    @Lob
    @Column(name = "document_type", nullable = false)
    private String documentType;

    @Column(name = "document_url", nullable = false)
    private String documentUrl;

}