package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.DocumentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.DocumentEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentEntityRepository extends JpaRepository<DocumentEntity, DocumentEntityId> {
}
