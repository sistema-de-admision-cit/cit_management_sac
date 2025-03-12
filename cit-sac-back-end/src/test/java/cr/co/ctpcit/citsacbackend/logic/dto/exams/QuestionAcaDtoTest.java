package cr.co.ctpcit.citsacbackend.logic.dto.exams;

import cr.co.ctpcit.citsacbackend.TestProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class QuestionAcaDtoTest {
  @Autowired
  JacksonTester<QuestionAcaDto> jacksonTester;

  @Test
  void serializeQuestionAcaDto() throws IOException {
    Question questionAca = TestProvider.provideQuestionAcaDto();

    assertThat(jacksonTester.write((QuestionAcaDto) questionAca)).isStrictlyEqualToJson(
        "QuestionAcaDtoJsonExpected.json");
    assertThat(jacksonTester.write((QuestionAcaDto) questionAca)).hasJsonPathNumberValue("@.id");
    assertThat(jacksonTester.write((QuestionAcaDto) questionAca)).extractingJsonPathNumberValue("@.id").isEqualTo(2);
    assertThat(jacksonTester.write((QuestionAcaDto) questionAca)).hasJsonPathStringValue("@.questionText");
  }

  @Test
  void deserializeQuestionAcaDto() throws IOException {
    String expected = """
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
              "option": "π * radio^2",
              "selected": true
            },
            {
              "id": 2,
              "isCorrect": false,
              "option": "2 * radio",
              "selected": false
            },
            {
              "id": 3,
              "isCorrect": false,
              "option": "π * diámetro",
              "selected": false
            },
            {
              "id": 4,
              "isCorrect": false,
              "option": "radio * altura",
              "selected": false
            }
          ]
        }
        """;

    Question questionAca = TestProvider.provideQuestionAcaDto();

    assertThat(jacksonTester.parse(expected)).isEqualTo(questionAca);
    assertThat(jacksonTester.parseObject(expected).getId()).isEqualTo(2);
    assertThat(jacksonTester.parseObject(expected).getQuestionText()).isEqualTo(
        "¿Como se calcula el area de un circulo?");
  }
}
