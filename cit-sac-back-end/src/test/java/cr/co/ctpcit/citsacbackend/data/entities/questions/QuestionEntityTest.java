package cr.co.ctpcit.citsacbackend.data.entities.questions;

import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionLevel;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import cr.co.ctpcit.citsacbackend.data.enums.SelectionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class QuestionEntityTest {
  @Autowired
  JacksonTester<QuestionEntity> json;

  @Test
  void testSerialize() throws Exception {
    QuestionEntity question = new QuestionEntity();
    question.setId(1L);
    question.setQuestionType(QuestionType.ACA);
    question.setQuestionText("Si se suma 11 más 5, existe un escenario donde el resultado es 4.");
    question.setImageUrl(null);
    question.setQuestionGrade(Grades.EIGHTH);
    question.setQuestionLevel(QuestionLevel.HARD);
    question.setSelectionType(SelectionType.SINGLE);
    question.setDeleted(false);

    QuestionOptionEntity option1 = new QuestionOptionEntity();
    option1.setId(1L);
    option1.setIsCorrect(false);
    option1.setOption("Falso");

    QuestionOptionEntity option2 = new QuestionOptionEntity();
    option2.setId(2L);
    option2.setIsCorrect(true);
    option2.setOption("Verdadero");

    question.addQuestionOption(option1);
    question.addQuestionOption(option2);

    assertThat(json.write(question)).isStrictlyEqualToJson("QuestionEntityJsonExpected.json");
    assertThat(json.write(question)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(question)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
    assertThat(json.write(question)).hasJsonPathStringValue("@.questionType");
    assertThat(json.write(question)).extractingJsonPathStringValue("@.selectionType")
        .isEqualTo("SINGLE");
  }

  @Test
  void testDeserialize() throws Exception {
    String expected = """
        {
          "id": 1,
          "questionType": "ACA",
          "questionText": "Si se suma 11 más 5, existe un escenario donde el resultado es 4.",
          "imageUrl": null,
          "questionGrade": "EIGHTH",
          "questionLevel": "HARD",
          "selectionType": "SINGLE",
          "deleted": false,
          "questionOptions": [
            {
              "id": 1,
              "option": "Falso",
              "isCorrect": false
            },
            {
              "id": 2,
              "option": "Verdadero",
              "isCorrect": true
            }
          ]
        }
        """;
    QuestionEntity question = new QuestionEntity();
    question.setId(1L);
    question.setQuestionType(QuestionType.ACA);
    question.setQuestionText("Si se suma 11 más 5, existe un escenario donde el resultado es 4.");
    question.setImageUrl(null);
    question.setQuestionGrade(Grades.EIGHTH);
    question.setQuestionLevel(QuestionLevel.HARD);
    question.setSelectionType(SelectionType.SINGLE);
    question.setDeleted(false);

    QuestionOptionEntity option1 = new QuestionOptionEntity();
    option1.setId(1L);
    option1.setIsCorrect(false);
    option1.setOption("Falso");

    QuestionOptionEntity option2 = new QuestionOptionEntity();
    option2.setId(2L);
    option2.setIsCorrect(true);
    option2.setOption("Verdadero");

    question.addQuestionOption(option1);
    question.addQuestionOption(option2);

    assertThat(json.parse(expected)).isEqualTo(question);
    assertThat(json.parseObject(expected).getId()).isEqualTo(1);
    assertThat(json.parseObject(expected).getQuestionType()).isEqualTo(QuestionType.ACA);
  }
}
