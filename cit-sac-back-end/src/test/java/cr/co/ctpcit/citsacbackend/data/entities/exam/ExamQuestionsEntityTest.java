package cr.co.ctpcit.citsacbackend.data.entities.exam;

import cr.co.ctpcit.citsacbackend.data.entities.questions.QuestionEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class ExamQuestionsEntityTest {

    @Autowired
    JacksonTester<ExamQuestionsEntity> json;

    @Test
    void testSerialize() throws Exception {
        ExamEntity exam = new ExamEntity();
        exam.setId(1L);

        QuestionEntity question = new QuestionEntity();
        question.setId(2L);

        ExamQuestionsEntity examQuestion = ExamQuestionsEntity.builder()
                .id(1L)
                .exam(exam)
                .question(question)
                .answer("Sample Answer")
                .build();

        assertThat(json.write(examQuestion)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(examQuestion)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
        assertThat(json.write(examQuestion)).extractingJsonPathStringValue("@.answer").isEqualTo("Sample Answer");
    }

    @Test
    void testDeserialize() throws Exception {
        String expected = """
            {
              "id": 1,
              "answer": "Sample Answer"
            }
            """;
        ExamQuestionsEntity examQuestion = json.parseObject(expected);
        assertEquals(1L, examQuestion.getId());
        assertEquals("Sample Answer", examQuestion.getAnswer());
    }
}