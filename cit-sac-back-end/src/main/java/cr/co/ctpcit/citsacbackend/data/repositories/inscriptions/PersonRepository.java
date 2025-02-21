package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
  Optional<PersonEntity> findByIdNumber(String idNumber);

  List<PersonEntity> findByFirstNameContainingOrFirstSurnameContainingOrSecondSurnameContaining(
      String firstName, String lastName, String secondLastName);
}
