package cr.co.ctpcit.citsacbackend.data.repositories.exam;

import cr.co.ctpcit.citsacbackend.data.entities.exam.ExamEntity;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExamRepository extends JpaRepository<ExamEntity, Long>, JpaSpecificationExecutor<ExamEntity> {
    /**
     * Get all exams in a paginated way.
     */
    @Override
    Page<ExamEntity> findAll(Pageable pageable);

    /**
     * Get exams by type.
     */
    List<ExamEntity> findByExamType(QuestionType examType);

    /**
     * Soft delete an exam.
     */
    @Modifying
    @Transactional
    @Query("UPDATE ExamEntity e SET e.responses = NULL WHERE e.id = ?1")
    void softDeleteExam(Long id);
}