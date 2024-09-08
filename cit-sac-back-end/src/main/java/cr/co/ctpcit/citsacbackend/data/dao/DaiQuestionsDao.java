package cr.co.ctpcit.citsacbackend.data.dao;


import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiQuestionsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component("actividadComercialDao")
public interface DaiQuestionsDao extends CrudRepository<DaiQuestionsEntity,Integer> {
}
