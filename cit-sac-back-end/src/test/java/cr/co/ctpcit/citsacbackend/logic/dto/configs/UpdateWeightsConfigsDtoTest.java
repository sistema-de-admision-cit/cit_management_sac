package cr.co.ctpcit.citsacbackend.logic.dto.configs;

import cr.co.ctpcit.citsacbackend.TestProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class UpdateWeightsConfigsDtoTest {
  @Autowired
  JacksonTester<UpdateWeightsConfigsDto> json;

  @Test
  void serializeJson() throws Exception {
    UpdateWeightsConfigsDto updateWeightsConfigsDto = TestProvider.provideUpdateWeightsConfigsDto();

    assertThat(json.write(updateWeightsConfigsDto)).isStrictlyEqualToJson(
        "UpdateWeightsConfigsDtoJsonExpected.json");
    assertThat(json.write(updateWeightsConfigsDto)).hasJsonPathNumberValue("@.prevGradesWeight");
    assertThat(json.write(updateWeightsConfigsDto)).extractingJsonPathNumberValue(
        "@.prevGradesWeight").isEqualTo(0.4);
    assertThat(json.write(updateWeightsConfigsDto)).hasJsonPathNumberValue("@.academicWeight");
    assertThat(json.write(updateWeightsConfigsDto)).extractingJsonPathNumberValue(
        "@.academicWeight").isEqualTo(0.6);
  }

  @Test
  void deserializeJson() throws Exception {
    String content = """
        {
          "prevGradesWeight": 0.4,
          "academicWeight": 0.6
        }
        """;

    UpdateWeightsConfigsDto updateWeightsConfigsDto = json.parse(content).getObject();

    assertEquals(0.4, updateWeightsConfigsDto.prevGradesWeight());
    assertEquals(0.6, updateWeightsConfigsDto.academicWeight());
  }
}
