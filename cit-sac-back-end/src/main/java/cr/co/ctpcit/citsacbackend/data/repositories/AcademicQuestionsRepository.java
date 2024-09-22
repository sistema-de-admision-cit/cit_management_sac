package cr.co.ctpcit.citsacbackend.data.repositories;


import cr.co.ctpcit.citsacbackend.data.entities.exams.academic.AcademicQuestionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcademicQuestionsRepository
    extends JpaRepository<AcademicQuestionsEntity, Integer> {
  List<AcademicQuestionsEntity> findByQuestionTextContaining(String questionText);
}

