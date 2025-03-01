package cr.co.ctpcit.citsacbackend.logic.dto.questions;

import cr.co.ctpcit.citsacbackend.TestProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class QuestionDtoTest {
  @Autowired
  private JacksonTester<QuestionDto> json;

  @Test
  void serializeJson() throws IOException {
    QuestionDto question = TestProvider.provideQuestionDto();

    assertThat(json.write(question)).isStrictlyEqualToJson("QuestionDtoJsonExpected.json");
    assertThat(json.write(question)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(question)).extractingJsonPathNumberValue("@.id").isEqualTo(2);
    assertThat(json.write(question)).hasJsonPathStringValue("@.questionText");
    assertThat(json.write(question)).extractingJsonPathStringValue("@.questionText")
        .isEqualTo("¿Como se calcula el area de un circulo?");

  }

  @Test
  void deserializeJson() throws IOException {
    String content = """
        {
          "id": 2,
          "questionType": "ACA",
          "questionText": "¿Como se calcula el area de un circulo?",
          "imageUrl": null,
          "questionGrade": "FIRST",
          "questionLevel": "EASY",
          "selectionType": "SINGLE",
          "deleted": false,
          "questionOptions": [
            {
              "id": 1,
              "isCorrect": true,
              "option": "π * radio^2"
            },
            {
              "id": 2,
              "isCorrect": false,
              "option": "2 * radio"
            },
            {
              "id": 3,
              "isCorrect": false,
              "option": "π * diámetro"
            },
            {
              "id": 4,
              "isCorrect": false,
              "option": "radio * altura"
            }
          ]
        }
        """;

    QuestionDto question = TestProvider.provideQuestionDto();

    assertThat(json.parse(content)).isEqualTo(question);
    assertThat(json.parseObject(content).id()).isEqualTo(2);
    assertThat(json.parseObject(content).questionText()).isEqualTo(
        "¿Como se calcula el area de un circulo?");
  }
}
