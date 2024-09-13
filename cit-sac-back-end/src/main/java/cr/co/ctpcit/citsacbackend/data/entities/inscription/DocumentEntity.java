package cr.co.ctpcit.citsacbackend.data.entities.inscription;

import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.validation.annotation.Validated;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Validated
@Table(name = "tbl_documents")
public class DocumentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id", nullable = false)
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Long id;

    @NotNull(message = "Es obligatorio que exista un registro asociado.")
    @ManyToOne(optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false)
    @ToString.Exclude
    private EnrollmentEntity enrollment;

    @NotNull(message = "Es obligatorio que se indique el tipo de documento.")
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private DocType documentType;

    @Size(max = 255)
    @NotNull(message = "Es obligatorio que se indique la URL del documento.")
    @NotBlank(message = "Es obligatorio que se indique la URL del documento.")
    @Column(name = "document_url", nullable = false)
    private String documentUrl;
}