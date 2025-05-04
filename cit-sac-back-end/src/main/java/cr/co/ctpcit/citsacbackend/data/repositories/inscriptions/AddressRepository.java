package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link AddressEntity} entities.
 * Inherits standard CRUD operations from {@link JpaRepository}.
 */
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
