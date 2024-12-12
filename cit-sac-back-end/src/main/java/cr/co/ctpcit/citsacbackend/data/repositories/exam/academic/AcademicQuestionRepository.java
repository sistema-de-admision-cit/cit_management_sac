package cr.co.ctpcit.citsacbackend.data.repositories.exam.academic;

import cr.co.ctpcit.citsacbackend.data.entities.exams.academic.AcademicQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicQuestionRepository extends JpaRepository<AcademicQuestionEntity, Integer> {

}