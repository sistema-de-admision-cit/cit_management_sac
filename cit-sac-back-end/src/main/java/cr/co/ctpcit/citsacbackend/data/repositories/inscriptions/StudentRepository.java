package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link StudentEntity} entities. Provides custom query methods
 * to retrieve students by specific criteria.
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
  @Query(
      "SELECT s FROM StudentEntity s " + "JOIN EnrollmentEntity e ON s.id = e.student.id " + "WHERE e.status IN ('PENDING','ELIGIBLE','INELIGIBLE') " + "AND s.studentPerson.idNumber LIKE CONCAT('%', ?1, '%')" + "GROUP BY s.id")
  List<StudentEntity> findStudentByLikeIdNumberWithEnrollmentInProcess(String value,
      Pageable pageable);

  /**
   * Finds all students whose associated {@link PersonEntity} is within the provided list of
   * persons.
   *
   * @param persons the list of {@link PersonEntity} to filter students by
   * @return a list of {@link StudentEntity} associated with the provided persons
   */
  List<StudentEntity> findAllByStudentPersonIn(List<PersonEntity> persons);

    /**
     * Finds all students whose associated {@link EnrollmentEntity} is in process (i.e., has a status
     * of 'PENDING', 'ELIGIBLE', or 'INELIGIBLE').
     *
     * @param pageable the pagination information
     * @return a page of {@link StudentEntity} with enrollments in process
     */
  @Query(
      "SELECT s FROM StudentEntity s " +
          "JOIN EnrollmentEntity e ON s.id = e.student.id " +
          "WHERE e.status IN ('PENDING','ELIGIBLE','INELIGIBLE') " +
          "GROUP BY s.id")
  Page<StudentEntity> findAllWithEnrollmentsInProcess(Pageable pageable);

  /**
   * Finds students whose associated {@link PersonEntity}'s first name, first surname, or second
   * surname contains the specified value.
   *
   * @param value the value to search for within the first name, first surname, or second surname
   * @return a list of {@link StudentEntity} that match the search criteria
   */
  @Query(
          "SELECT s FROM StudentEntity s " +
                  "JOIN PersonEntity p ON p.id = s.id " +
                  "JOIN EnrollmentEntity e ON s.id = e.student.id " +
                  "WHERE (LOWER(p.firstName) LIKE %:value% " +
                  "OR LOWER(p.idNumber) LIKE %:value% " +
                  "OR LOWER(p.firstSurname) LIKE %:value% " +
                  "OR LOWER(p.secondSurname) LIKE %:value%) " +
                  "AND e.status IN ('PENDING','ELIGIBLE','INELIGIBLE') " +
                  "GROUP BY s.id")
  List<StudentEntity> findAllByValueWithEnrollmentInProcess(String value, Pageable pageable);


  @Query(
          "SELECT s FROM StudentEntity s " +
                  "JOIN PersonEntity p ON p.id = s.id " +
                  "JOIN EnrollmentEntity e ON s.id = e.student.id " +
                  "WHERE (LOWER(p.firstName) LIKE %:value% " +
                  "OR LOWER(p.idNumber) LIKE %:value% " +
                  "OR LOWER(p.firstSurname) LIKE %:value% " +
                  "OR LOWER(p.secondSurname) LIKE %:value%) " +
                  "AND e.status IN ('ELIGIBLE','ACCEPTED','REJECTED') " +
                  "GROUP BY s.id")
  List<StudentEntity> findAllByValueWithExamsInProcess(String value, Pageable pageable);


  @Query(
          "SELECT COUNT(DISTINCT s.id) FROM StudentEntity s " +
                  "JOIN PersonEntity p ON p.id = s.id " +
                  "JOIN EnrollmentEntity e ON s.id = e.student.id " +
                  "WHERE (LOWER(p.firstName) LIKE %:value% " +
                  "OR LOWER(p.idNumber) LIKE %:value% " +
                  "OR LOWER(p.firstSurname) LIKE %:value% " +
                  "OR LOWER(p.secondSurname) LIKE %:value%) " +
                  "AND e.status IN ('PENDING','ELIGIBLE','INELIGIBLE')")
  Long countStudentsWithEnrollmentsInProcessByValue(String value);

}
