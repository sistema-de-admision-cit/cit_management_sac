package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link PersonEntity} entities.
 * Provides custom query methods to retrieve persons by specific criteria.
 */
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

  /**
   * Finds a person by their ID number.
   *
   * @param idNumber the ID number of the person
   * @return an {@link Optional} containing the {@link PersonEntity} if found, or empty if not
   */
  Optional<PersonEntity> findByIdNumber(String idNumber);

  /**
   * Finds persons whose first name, first surname, or second surname contains the specified value.
   *
   * @param value the value to search for within the first name, first surname, or second surname
   * @return a list of {@link PersonEntity} that match the search criteria
   */
  @Query(
          "SELECT p FROM PersonEntity p WHERE p.firstName LIKE %:value% OR p.firstSurname LIKE %:value% OR p.secondSurname LIKE %:value%")
  List<PersonEntity> findByFirstNameFirstSurnameSecondSurnameLike(@Param("value") String value);

}
