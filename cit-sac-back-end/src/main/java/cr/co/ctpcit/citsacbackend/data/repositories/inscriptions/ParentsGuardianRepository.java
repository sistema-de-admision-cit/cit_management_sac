package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.ParentsGuardianEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParentsGuardianRepository extends JpaRepository<ParentsGuardianEntity, Long> {
  Optional<ParentsGuardianEntity> findParentsGuardianByIdNumber(String idNumber);
}
