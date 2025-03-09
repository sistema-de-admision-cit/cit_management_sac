package cr.co.ctpcit.citsacbackend.data.entities.exam;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ExamEntityTest {
    @Autowired
    JacksonTester<ExamEntity> json;

    @Test
    void testSerialize() throws Exception {
        EnrollmentEntity enrollment = new EnrollmentEntity();
        enrollment.setId(1L);

        ExamEntity exam = new ExamEntity();
        exam.setId(1L);
        exam.setEnrollment(enrollment);
        exam.setExamDate(Instant.parse("2024-01-01T10:00:00Z"));
        exam.setExamType(QuestionType.ACA);
        exam.setResponses("{\"answer\": \"true\"}");
        exam.setExamQuestions(new ArrayList<>());

        assertThat(json.write(exam)).isStrictlyEqualToJson("ExamEntityJsonExpected.json");
        assertThat(json.write(exam)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(exam)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
        assertThat(json.write(exam)).hasJsonPathStringValue("@.examType");
        assertThat(json.write(exam)).extractingJsonPathStringValue("@.examType").isEqualTo("ACA");

    }

    @Test
    void testDeserialize() throws Exception {
        String expected = """
            {
              "id": 1,
              "enrollment": { "id": 1 },
              "examDate": "2024-01-01T10:00:00Z",
              "examType": "ACA",
              "responses": "{\"answer\": \"true\"}",
              "examQuestions": []
            }
            """;

        ExamEntity exam = json.parseObject(expected);
        assertThat(exam.getId()).isEqualTo(1);
        assertThat(exam.getExamType()).isEqualTo(QuestionType.ACA);
        assertThat(exam.getResponses()).isEqualTo("{\"answer\": \"true\"}");
        assertThat(exam.getEnrollment().getId()).isEqualTo(1);
    }
}