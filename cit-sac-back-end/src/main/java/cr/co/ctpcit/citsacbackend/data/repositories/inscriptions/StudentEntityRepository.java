package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentEntityRepository extends JpaRepository<StudentEntity, Long> {
}
