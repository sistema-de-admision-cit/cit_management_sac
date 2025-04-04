package cr.co.ctpcit.citsacbackend.logic.dto.exams;

import cr.co.ctpcit.citsacbackend.TestProvider;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class QuestionDaiDtoTest {
  @Autowired
  JacksonTester<QuestionDaiDto> tester;

  @Test
  void serializeJson() throws IOException {
    QuestionDaiDto questionDai = TestProvider.provideQuestionDaiDto();

    assertThat(tester.write(questionDai)).isEqualToJson(
        "QuestionDaiDtoJsonExpected.json");
    assertThat(tester.write(questionDai)).hasJsonPathNumberValue("@.id");
    assertThat(tester.write(questionDai)).extractingJsonPathNumberValue("@.id")
        .isEqualTo(1);
    assertThat(tester.write(questionDai)).hasJsonPathStringValue("@.questionText");
  }

  @Test
  void deserializeJson() throws IOException {
    String expected = """
        {
          "id": 1,
          "questionType": "DAI",
          "questionText": "¿Cómo te sientes el día de hoy?",
          "imageUrl": null,
          "selectionType": "PARAGRAPH",
          "deleted": false,
          "response": "Me siento muy bien, aunque con un poco de sueño."
        }
        """;

    QuestionDaiDto questionDai = TestProvider.provideQuestionDaiDto();

    assertThat(tester.parse(expected)).isEqualTo(questionDai);
    AssertionsForClassTypes.assertThat(tester.parseObject(expected).id()).isEqualTo(1);
    AssertionsForClassTypes.assertThat(tester.parseObject(expected).questionText())
        .isEqualTo("¿Cómo te sientes el día de hoy?");
  }
}
