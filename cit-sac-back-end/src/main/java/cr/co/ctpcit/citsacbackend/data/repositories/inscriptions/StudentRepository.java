package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

  Optional<StudentEntity> findStudentEntityByStudentPerson_IdNumber(String idNumber);

  List<StudentEntity> findStudentByStudentPerson_IdNumberContaining(String value, Pageable pageable);

  List<StudentEntity> findAllByStudentPersonIn(List<PersonEntity> persons);

  @Query(
      "SELECT s FROM StudentEntity s JOIN PersonEntity p ON p.id = s.id WHERE p.firstName LIKE %:value% OR p.firstSurname LIKE %:value% OR p.secondSurname LIKE %:value%")
  List<StudentEntity> findAllByValue(String value);
}
