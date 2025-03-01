package cr.co.ctpcit.citsacbackend.logic.dto.configs;

import cr.co.ctpcit.citsacbackend.TestProvider;
import cr.co.ctpcit.citsacbackend.data.enums.Configurations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class SystemConfigDtoTest {

  @Autowired
  JacksonTester<SystemConfigDto> json;

  @Test
  void serializeJson() throws IOException {
    SystemConfigDto systemConfigDto = TestProvider.provideSystemConfigDto();

    assertThat(json.write(systemConfigDto)).isStrictlyEqualToJson(
        "SystemConfigDtoJsonExpected.json");
    assertThat(this.json.write(systemConfigDto)).extractingJsonPathStringValue("@.configValue")
        .isEqualTo("contactocit@ctpcit.co.cr");
  }

  @Test
  void deserializeJson() throws IOException {
    String content = """
        {
          "id": 1,
          "configName": "EMAIL_CONTACT",
          "configValue": "contactocit@ctpcit.co.cr"
        }
        """;

    SystemConfigDto systemConfigDto = this.json.parse(content).getObject();

    assertThat(json.parse(content)).isEqualTo(systemConfigDto);
    assertThat(json.parseObject(content).id()).isEqualTo(1);
    assertThat(json.parseObject(content).configName()).isEqualTo(Configurations.EMAIL_CONTACT);
  }
}
