package cr.co.ctpcit.citsacbackend.data.repositories;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.ParentGuardianStudentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscription.ParentGuardianStudentEntityId;
import cr.co.ctpcit.citsacbackend.data.entities.inscription.ParentsGuardianEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscription.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParentGuardianStudentRepository extends JpaRepository<ParentGuardianStudentEntity, ParentGuardianStudentEntityId> {

    Optional<ParentGuardianStudentEntity> findParentGuardianStudentEntityByStudentAndParentGuardian(StudentEntity studentEntity, ParentsGuardianEntity parent);
}