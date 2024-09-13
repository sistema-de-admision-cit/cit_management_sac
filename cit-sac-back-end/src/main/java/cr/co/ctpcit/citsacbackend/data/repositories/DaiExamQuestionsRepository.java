package cr.co.ctpcit.citsacbackend.data.repositories;

import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiExamQuestionsEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiExamQuestionsEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaiExamQuestionsRepository extends JpaRepository<DaiExamQuestionsEntity, DaiExamQuestionsEntityId>{
}
