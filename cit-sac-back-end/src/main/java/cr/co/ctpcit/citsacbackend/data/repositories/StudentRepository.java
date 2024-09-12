package cr.co.ctpcit.citsacbackend.data.repositories;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.StudentEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for the StudentEntity class.
 */
@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
    Optional<StudentEntity> findStudentByIdNumber(@NotNull @Size(min = 9, max = 20) String idNumber);
}