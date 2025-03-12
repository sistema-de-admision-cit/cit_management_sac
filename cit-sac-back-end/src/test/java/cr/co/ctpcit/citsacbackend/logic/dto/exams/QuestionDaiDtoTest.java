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
    Question questionDai = TestProvider.provideQuestionDaiDto();

    assertThat(tester.write((QuestionDaiDto) questionDai)).isEqualToJson(
        "QuestionDaiDtoJsonExpected.json");
    assertThat(tester.write((QuestionDaiDto) questionDai)).hasJsonPathNumberValue("@.id");
    assertThat(tester.write((QuestionDaiDto) questionDai)).extractingJsonPathNumberValue("@.id")
        .isEqualTo(1);
    assertThat(tester.write((QuestionDaiDto) questionDai)).hasJsonPathStringValue("@.questionText");
  }

  @Test
  void deserializeJson() throws IOException {
    String expected = """
        {
          "id": 1,
          "questionType": "DAI",
          "questionText": "¿Cómo te sientes el día de hoy?",
          "imageUrl": null,
          "questionGrade": "SECOND",
          "questionLevel": "EASY",
          "selectionType": "PARAGRAPH",
          "deleted": false,
          "response": "Me siento muy bien, aunque con un poco de sueño."
        }
        """;

    Question questionDai = TestProvider.provideQuestionDaiDto();

    assertThat(tester.parse(expected)).isEqualTo((QuestionDaiDto) questionDai);
    AssertionsForClassTypes.assertThat(tester.parseObject(expected).getId()).isEqualTo(1);
    AssertionsForClassTypes.assertThat(tester.parseObject(expected).getQuestionText())
        .isEqualTo("¿Cómo te sientes el día de hoy?");
  }
}
