package cr.co.ctpcit.citsacbackend.data.entities.questions;

import cr.co.ctpcit.citsacbackend.data.enums.Grades;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionLevel;
import cr.co.ctpcit.citsacbackend.data.enums.QuestionType;
import cr.co.ctpcit.citsacbackend.data.enums.SelectionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class QuestionOptionEntityTest {
  @Autowired
  JacksonTester<QuestionOptionEntity> json;

  @Test
  void testSerialize() throws Exception {
    QuestionEntity question = new QuestionEntity(1L, QuestionType.ACA, "null", null, Grades.FIRST,
        QuestionLevel.EASY, SelectionType.SINGLE, false, new ArrayList<>());

    QuestionOptionEntity option = new QuestionOptionEntity();
    option.setId(1L);
    option.setIsCorrect(false);
    option.setOption("Falso");

    question.addQuestionOption(option);

    assertThat(json.write(option)).isStrictlyEqualToJson("QuestionOptionEntityJsonExpected.json");
    assertThat(json.write(option)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
    assertThat(json.write(option)).hasJsonPathStringValue("@.option");
    assertThat(json.write(option)).extractingJsonPathStringValue("@.option").isEqualTo("Falso");
  }

  @Test
  void testDeserialize() throws Exception {
    String expected = """
        {
          "id": 1,
          "isCorrect": false,
          "option": "Falso"
        }
        """;
    QuestionOptionEntity option = json.parseObject(expected);
    assertEquals(1L, option.getId());
    assertFalse(option.getIsCorrect());
    assertEquals("Falso", option.getOption());
  }
}
