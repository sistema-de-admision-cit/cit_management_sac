package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.StudentEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for the StudentEntity class.
 */
@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
  Optional<StudentEntity> findStudentByIdNumber(@NotNull @Size(min = 9, max = 20) String idNumber);

  List<StudentEntity> findStudentByIdNumberContaining(
      @NotNull @Size(min = 9, max = 20) String idNumber);

  List<StudentEntity> findByFirstNameContainingOrFirstSurnameContainingOrSecondSurnameContaining(
      @NotBlank String firstName, @NotBlank String firstSurname, @NotBlank String secondSurname);

  Optional<StudentEntity> findStudentById(Long id);
}
