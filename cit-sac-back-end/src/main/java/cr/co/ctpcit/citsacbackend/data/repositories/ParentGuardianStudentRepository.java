package cr.co.ctpcit.citsacbackend.data.repositories;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.ParentGuardianStudentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscription.ParentGuardianStudentEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for {@link ParentGuardianStudentEntity}
 */
@Repository
public interface ParentGuardianStudentRepository extends JpaRepository<ParentGuardianStudentEntity, ParentGuardianStudentEntityId> {
    List<ParentGuardianStudentEntity> findAllByStudentId(Integer studentId);
}