package cr.co.ctpcit.citsacbackend.data.entities.questions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class QuestionOptionEntityTest {
  @Autowired
  JacksonTester<QuestionOptionEntity> json;

  @Test
  void testSerialize() throws Exception {
    QuestionOptionEntity option = new QuestionOptionEntity();
    option.setId(new QuestionOptionEntityId(1L, 1L));
    option.setIsCorrect(false);
    option.setOption("Falso");

    assertThat(json.write(option)).isStrictlyEqualToJson("QuestionOptionEntityJsonExpected.json");
    assertThat(json.write(option)).hasJsonPathValue("@.id");
    assertThat(json.write(option)).extractingJsonPathNumberValue("@.id.questionId").isEqualTo(1);
    assertThat(json.write(option)).hasJsonPathStringValue("@.option");
    assertThat(json.write(option)).extractingJsonPathStringValue("@.option").isEqualTo("Falso");
  }

  @Test
  void testDeserialize() throws Exception {
    String expected = """
        {
        "id": {
            "questionId": 1,
            "optionId": 1
        },
        "isCorrect": false,
        "option": "Falso"
        }
        """;
    QuestionOptionEntity option = json.parseObject(expected);
    assertEquals(1L, option.getId().getQuestionId());
    assertEquals(1L, option.getId().getOptionId());
    assertFalse(option.getIsCorrect());
    assertEquals("Falso", option.getOption());
  }
}
