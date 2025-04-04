package cr.co.ctpcit.citsacbackend.logic.dto.exams;

import cr.co.ctpcit.citsacbackend.TestProvider;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.QuestionAcaDto;
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
    QuestionAcaDto questionAca = TestProvider.provideQuestionAcaDto();

    assertThat(
        jacksonTester.write(questionAca)).isStrictlyEqualToJson("QuestionAcaDtoJsonExpected.json");
    assertThat(jacksonTester.write(questionAca)).hasJsonPathNumberValue("@.id");
    assertThat(jacksonTester.write(questionAca)).extractingJsonPathNumberValue(
        "@.id").isEqualTo(2);
    assertThat(jacksonTester.write(questionAca)).hasJsonPathStringValue(
        "@.questionText");
  }

  @Test
  void deserializeQuestionAcaDto() throws IOException {
    String expected = """
        {
          "id": 2,
          "questionType": "ACA",
          "questionText": "¿Como se calcula el area de un circulo?",
          "imageUrl": null,
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

    QuestionAcaDto questionAca = TestProvider.provideQuestionAcaDto();

    assertThat(jacksonTester.parse(expected)).isEqualTo(questionAca);
    assertThat(jacksonTester.parseObject(expected).id()).isEqualTo(2);
    assertThat(jacksonTester.parseObject(expected).questionText()).isEqualTo(
        "¿Como se calcula el area de un circulo?");
  }
}
