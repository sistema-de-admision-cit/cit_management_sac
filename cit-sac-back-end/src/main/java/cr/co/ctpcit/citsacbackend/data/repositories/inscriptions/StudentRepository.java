package cr.co.ctpcit.citsacbackend.data.repositories.inscriptions;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.PersonEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

  Optional<StudentEntity> findStudentEntityByStudentPerson_IdNumber(String idNumber);

  List<StudentEntity> findStudentByStudentPerson_IdNumberContaining(String value);

  List<StudentEntity> findAllByStudentPersonIn(List<PersonEntity> persons);
}
