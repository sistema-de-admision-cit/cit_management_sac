package cr.co.ctpcit.citsacbackend.logic.dto.exams;

import cr.co.ctpcit.citsacbackend.TestProvider;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.dai.ExamDaiDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class ExamDaiDtoTest {

  @Autowired
  JacksonTester<ExamDaiDto> tester;

  @Test
  void serializeJsonExamDai() throws IOException {
    ExamDaiDto exam = TestProvider.provideDaiExamDto();

    assertThat(tester.write(exam)).isEqualToJson("ExamDaiDtoJsonExpected.json");
    assertThat(tester.write(exam)).hasJsonPathNumberValue("@.id");
    assertThat(tester.write(exam)).extractingJsonPathNumberValue("@.id").isEqualTo(2);
    assertThat(tester.write(exam)).hasJsonPathStringValue("@.examType");
  }

  @Test
  void deserializeJsonExamDai() throws IOException {
    String expected = """
        {
          "id": 2,
          "enrollment": 1,
          "examDate": "1970-01-01T00:00:00Z",
          "examType": "DAI",
          "responses": [
            {
              "id": 1,
              "questionType": "DAI",
              "questionText": "¿Cómo te sientes el día de hoy?",
              "imageUrl": null,
              "selectionType": "PARAGRAPH",
              "deleted": false,
              "response": "Me siento muy bien, aunque con un poco de sueño."
            }
          ]
        }
        """;

    ExamDaiDto examDto = TestProvider.provideDaiExamDto();

    assertThat(tester.parseObject(expected).id()).isEqualTo(2);
    assertThat(tester.parseObject(expected).examType()).isEqualTo(examDto.examType());
    assertThat(tester.parseObject(expected).responses().size())
        .isEqualTo(examDto.responses().size());
  }
}
