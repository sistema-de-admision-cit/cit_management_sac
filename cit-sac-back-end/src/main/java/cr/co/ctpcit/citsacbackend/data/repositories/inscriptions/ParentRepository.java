package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.ParentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link ParentEntity} entities.
 * Inherits standard CRUD operations from {@link JpaRepository}.
 */

public interface ParentRepository extends JpaRepository<ParentEntity, Long> {
}
