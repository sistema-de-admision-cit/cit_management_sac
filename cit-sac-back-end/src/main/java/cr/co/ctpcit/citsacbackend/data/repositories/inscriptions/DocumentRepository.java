package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing {@link DocumentEntity} entities.
 * Provides a custom method to retrieve a document by its name.
 */
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {

  /**
   * Finds a document by its name.
   *
   * @param documentName the name of the document to search for
   * @return an {@link Optional} containing the {@link DocumentEntity} if found, or empty if not
   */
  Optional<DocumentEntity> findByDocumentName(String documentName);
}
