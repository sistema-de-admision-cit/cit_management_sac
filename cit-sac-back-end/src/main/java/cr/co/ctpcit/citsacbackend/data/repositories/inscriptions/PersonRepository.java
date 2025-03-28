package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
  Optional<PersonEntity> findByIdNumber(String idNumber);

  @Query(
      "SELECT p FROM PersonEntity p WHERE p.firstName LIKE :value OR p.firstSurname LIKE :value OR p.secondSurname LIKE :value")
  List<PersonEntity> findByFirstNameFirstSurnameSecondSurnameLike(String value);
}
