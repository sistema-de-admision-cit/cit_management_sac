package cr.co.ctpcit.citsacbackend.logic.dto.auth;

import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ChangePasswordRequestDTOTest {
  @Autowired
  private JacksonTester<ChangePasswordRequestDTO> json;

  @MockBean
  StorageService storageService;

  @Test
  void testDeserialize() throws IOException {
    String expected = """
        {
          "currentPassword": "Campus",
          "newPassword": "exampleOfCorrect12",
          "confirmPassword": "exampleOfCorrect12"
        }
        """;

    assertThat(json.parse(expected)).isEqualTo(
        new ChangePasswordRequestDTO("Campus", "exampleOfCorrect12", "exampleOfCorrect12"));
    assertThat(json.parseObject(expected).currentPassword()).isEqualTo("Campus");
    assertThat(json.parseObject(expected).newPassword()).isEqualTo("exampleOfCorrect12");
    assertThat(json.parseObject(expected).confirmPassword()).isEqualTo("exampleOfCorrect12");
  }

  @Test
  void testSerialize() throws IOException {
    ChangePasswordRequestDTO requestDTO =
        new ChangePasswordRequestDTO("Campus", "exampleOfCorrect12", "exampleOfCorrect12");

    assertThat(json.write(requestDTO)).isEqualToJson("changePasswordExpected.json");
    assertThat(json.write(requestDTO)).hasJsonPathStringValue("@.currentPassword");
    assertThat(json.write(requestDTO)).hasJsonPathStringValue("@.newPassword");
    assertThat(json.write(requestDTO)).hasJsonPathStringValue("@.confirmPassword");
  }
}
