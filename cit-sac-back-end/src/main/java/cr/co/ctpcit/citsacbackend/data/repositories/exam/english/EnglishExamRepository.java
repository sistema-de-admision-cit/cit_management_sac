package cr.co.ctpcit.citsacbackend.data.repositories.exam.english;

import cr.co.ctpcit.citsacbackend.data.entities.exams.english.EnglishExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnglishExamRepository extends JpaRepository<EnglishExamEntity, Long> {
}
