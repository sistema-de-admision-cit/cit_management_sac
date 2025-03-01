package cr.co.ctpcit.citsacbackend.logic.dto.configs;

import cr.co.ctpcit.citsacbackend.TestProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class UpdateContactInfoConfigsDtoTest {
  @Autowired
  JacksonTester<UpdateContactInfoConfigsDto> json;

  @Test
  void serializeJson() throws IOException {
    UpdateContactInfoConfigsDto updateContactInfoConfigsDto =
        TestProvider.provideUpdateContactInfoConfigsDto();

    assertThat(json.write(updateContactInfoConfigsDto)).isStrictlyEqualToJson(
        "UpdateContactInfoConfigsDtoJsonExpected.json");
    assertThat(json.write(updateContactInfoConfigsDto)).hasJsonPathStringValue("@.emailContact");
    assertThat(json.write(updateContactInfoConfigsDto)).extractingJsonPathStringValue(
        "@.emailContact").isEqualTo("contactocit@ctpcit.co.cr");
  }

  @Test
  void deserializeJson() throws IOException {
    String expected = """
        {
          "emailContact": "contactocit@ctpcit.co.cr",
          "emailNotificationsContact": "notificaciones@ctpcit.co.cr",
          "whatsappContact": "88090041",
          "officeContact": "22370186",
          "instagramContact": "ComplejoEducativoCIT",
          "facebookContact": "ComplejoEducativoCIT"
        }
        """;

    UpdateContactInfoConfigsDto updateContactInfoConfigsDto = json.parse(expected).getObject();

    assertEquals("contactocit@ctpcit.co.cr", updateContactInfoConfigsDto.emailContact());
    assertEquals("22370186", updateContactInfoConfigsDto.officeContact());
    assertEquals("ComplejoEducativoCIT", updateContactInfoConfigsDto.instagramContact());
  }
}
