package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link StudentEntity} entities.
 * Provides custom query methods to retrieve students by specific criteria.
 */
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

  /**
   * Finds a student by their ID number.
   *
   * @param idNumber the ID number of the student
   * @return an {@link Optional} containing the {@link StudentEntity} if found, or empty if not
   */
  Optional<StudentEntity> findStudentEntityByStudentPerson_IdNumber(String idNumber);

  /**
   * Finds students whose ID number contains the specified value.
   *
   * @param value the value to search for within the ID number
   * @return a list of {@link StudentEntity} matching the search criteria
   */
  List<StudentEntity> findStudentByStudentPerson_IdNumberContaining(String value);

  /**
   * Finds all students whose associated {@link PersonEntity} is within the provided list of persons.
   *
   * @param persons the list of {@link PersonEntity} to filter students by
   * @return a list of {@link StudentEntity} associated with the provided persons
   */
  List<StudentEntity> findAllByStudentPersonIn(List<PersonEntity> persons);

  /**
   * Finds students whose associated {@link PersonEntity}'s first name, first surname, or second surname
   * contains the specified value.
   *
   * @param value the value to search for within the first name, first surname, or second surname
   * @return a list of {@link StudentEntity} that match the search criteria
   */
  @Query(
      "SELECT s FROM StudentEntity s JOIN PersonEntity p ON p.id = s.id WHERE p.firstName LIKE %:value% OR p.firstSurname LIKE %:value% OR p.secondSurname LIKE %:value%")
  List<StudentEntity> findAllByValue(String value);
}
