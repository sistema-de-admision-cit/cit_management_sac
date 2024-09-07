package cr.co.ctpcit.citsacbackend.data.entities.inscription;

import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tbl_documents")
public class DocumentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false)
    @ToString.Exclude
    private EnrollmentEntity enrollment;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private DocType documentType;

    @Size(max = 255)
    @NotNull
    @Column(name = "document_url", nullable = false)
    private String documentUrl;

}