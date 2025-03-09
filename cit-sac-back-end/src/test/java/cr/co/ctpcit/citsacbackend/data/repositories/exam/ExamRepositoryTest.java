package cr.co.ctpcit.citsacbackend.data.repositories.exam;

import cr.co.ctpcit.citsacbackend.data.entities.exam.ExamEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(showSql = false)
class ExamRepositoryTest {
    @Autowired
    private ExamRepository examRepository;

    private ExamEntity exam;

    @BeforeEach
    void setUp() {
        EnrollmentEntity enrollment = new EnrollmentEntity();
        enrollment.setId(1L); // Asegúrate de que exista en la base de datos o usa un Mock

        exam = new ExamEntity();
        exam.setEnrollment(enrollment); // Agregar la inscripción obligatoria
        exam.setExamType(QuestionType.ACA);
    }
    @Test
    @Transactional
    @Rollback
    @Order(1)
    void testSaveExamEntity() {
        ExamEntity savedExam = examRepository.save(exam);

        assertNotNull(savedExam);
        assertNotNull(savedExam.getId());
        assertEquals(exam, savedExam);
    }

    @Test
    @Order(2)
    void testFindByExamType() {
        examRepository.save(exam);
        List<ExamEntity> exams = examRepository.findByExamType(QuestionType.ACA);

        assertFalse(exams.isEmpty());
        assertEquals(QuestionType.ACA, exams.get(0).getExamType());
    }

    @Test
    @Transactional
    @Rollback
    @Order(3)
    void testSoftDeleteExam() {
        ExamEntity savedExam = examRepository.save(exam);
        Long examId = savedExam.getId();

        examRepository.softDeleteExam(examId);
        ExamEntity deletedExam = examRepository.findById(examId).orElse(null);

        assertNotNull(deletedExam);
        assertNull(deletedExam.getResponses());
    }
}
