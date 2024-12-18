package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonEntityRepository extends JpaRepository<PersonEntity, Long> {
}
