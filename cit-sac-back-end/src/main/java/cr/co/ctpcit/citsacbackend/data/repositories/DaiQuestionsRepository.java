package cr.co.ctpcit.citsacbackend.data.repositories;


import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiQuestionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaiQuestionsRepository extends JpaRepository<DaiQuestionsEntity, Integer> {

}
