package cr.co.ctpcit.citsacbackend.data.repositories;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.ParentGuardianStudentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscription.ParentGuardianStudentEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentGuardianStudentRepository extends JpaRepository<ParentGuardianStudentEntity, ParentGuardianStudentEntityId> {

}