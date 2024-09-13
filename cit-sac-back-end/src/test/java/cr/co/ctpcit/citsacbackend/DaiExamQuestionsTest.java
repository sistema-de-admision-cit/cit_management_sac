package cr.co.ctpcit.citsacbackend;

import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiExamQuestionsEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiExamQuestionsEntityId;
import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiExamsEntity;
import cr.co.ctpcit.citsacbackend.data.entities.exams.dai.DaiQuestionsEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

public class DaiExamQuestionsTest {
    private DaiExamQuestionsEntity daiExamQuestionsEntity;
    private DaiExamsEntity mockExam;
    private DaiQuestionsEntity mockQuestion;

    @BeforeEach
    void setUp() {
        // Mockea las entidades relacionadas
        mockExam = Mockito.mock(DaiExamsEntity.class);
        mockQuestion = Mockito.mock(DaiQuestionsEntity.class);

        // Inicializa la entidad a probar
        daiExamQuestionsEntity = DaiExamQuestionsEntity.builder()
                .id(new DaiExamQuestionsEntityId(1, 1))
                .examId(mockExam)
                .daiQuestions(mockQuestion)
                .studentAnswer("Mock answer")
                .build();
    }

    @Test
    void testStudentAnswer() {
        // Verifica que el campo studentAnswer esté correctamente asignado
        assertEquals("Mock answer", daiExamQuestionsEntity.getStudentAnswer());
    }

    @Test
    void testExamIdAssociation() {
        // Verifica que el examId esté correctamente asignado
        assertNotNull(daiExamQuestionsEntity.getExamId());
    }

    @Test
    void testQuestionAssociation() {
        // Verifica que daiQuestions esté correctamente asignado
        assertNotNull(daiExamQuestionsEntity.getDaiQuestions());
    }









}
