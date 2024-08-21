package cr.co.ctpcit.sac.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class TblDocumentId implements Serializable {
    private static final long serialVersionUID = 4216733081600729659L;
    @Column(name = "document_id", nullable = false)
    private Integer documentId;

    @Column(name = "enrollment_id", nullable = false)
    private Integer enrollmentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TblDocumentId entity = (TblDocumentId) o;
        return Objects.equals(this.enrollmentId, entity.enrollmentId) &&
                Objects.equals(this.documentId, entity.documentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentId, documentId);
    }

}