package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

  Optional<StudentEntity> findStudentEntityByStudentPerson_IdNumber(String idNumber);

  @Query("SELECT s FROM StudentEntity s " +
      "JOIN EnrollmentEntity e ON s.id = e.student.id " +
      "WHERE e.status IN ('PENDING','ELIGIBLE','INELIGIBLE') " +
      "AND s.studentPerson.idNumber LIKE CONCAT('%', ?1, '%')" +
      "GROUP BY s.id")
  List<StudentEntity> findStudentByLikeIdNumberWithEnrollmentInProcess(String value,
      Pageable pageable);

  List<StudentEntity> findAllByStudentPersonIn(List<PersonEntity> persons);

  @Query(
      "SELECT s FROM StudentEntity s " +
          "JOIN EnrollmentEntity e ON s.id = e.student.id " +
          "WHERE e.status IN ('PENDING','ELIGIBLE','INELIGIBLE') " +
          "GROUP BY s.id")
  Page<StudentEntity> findAllWithEnrollmentsInProcess(Pageable pageable);

  @Query(
      "SELECT s " +
          "FROM StudentEntity s " +
          "JOIN PersonEntity p ON p.id = s.id " +
          "JOIN EnrollmentEntity e ON s.id = e.student.id " +
          "WHERE LOWER(p.firstName) LIKE %:value% OR LOWER(p.fullSurname) LIKE %:value% " +
          "AND e.status IN ('PENDING','ELIGIBLE','INELIGIBLE')" +
          "GROUP BY s.id")
  List<StudentEntity> findAllByValueWithEnrollmentInProcess(String value);

  @Query(
      "SELECT COUNT(DISTINCT s.id) " +
          "FROM StudentEntity s " +
          "JOIN PersonEntity p ON p.id = s.id " +
          "JOIN EnrollmentEntity e ON s.id = e.student.id " +
          "WHERE LOWER(p.firstName) LIKE %:value% OR LOWER(p.fullSurname) LIKE %:value% " +
          "AND e.status IN ('PENDING','ELIGIBLE','INELIGIBLE')")
  Long countStudentsWithEnrollmentsInProcessByValue(String value);
}
