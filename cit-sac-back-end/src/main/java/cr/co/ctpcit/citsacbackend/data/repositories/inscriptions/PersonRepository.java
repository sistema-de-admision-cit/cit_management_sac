package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
  Optional<PersonEntity> findByIdNumber(String idNumber);

  @Query(
          "SELECT p FROM PersonEntity p WHERE p.firstName LIKE %:value% OR p.firstSurname LIKE %:value% OR p.secondSurname LIKE %:value%")
  List<PersonEntity> findByFirstNameFirstSurnameSecondSurnameLike(@Param("value") String value);

  /**
   * Repository query method to search for persons by first name, first surname, or second surname,
   * using a case-insensitive search. The search term is matched against any of the three fields.
   *
   * @param query The search term to look for in the first name, first surname, or second surname fields.
   * @param pageable The pagination information for the query results.
   * @return A page of person entities that match the search criteria.
   */

  @Query("SELECT p FROM PersonEntity p WHERE " +
          "LOWER(p.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
          "LOWER(p.firstSurname) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
          "LOWER(p.secondSurname) LIKE LOWER(CONCAT('%', :query, '%'))")
  Page<PersonEntity> findByNamesContaining(@Param("query") String query, Pageable pageable);
}
