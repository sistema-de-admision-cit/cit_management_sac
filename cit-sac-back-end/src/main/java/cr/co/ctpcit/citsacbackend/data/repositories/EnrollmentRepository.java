package cr.co.ctpcit.citsacbackend.data.repositories;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for the EnrollmentEntity class.
 */
@Repository
public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Integer> {
    @Override
    @NonNull
    Optional<EnrollmentEntity> findById(@NonNull Integer id);

    List<EnrollmentEntity> findAllByStudentId(Integer studentId);
}