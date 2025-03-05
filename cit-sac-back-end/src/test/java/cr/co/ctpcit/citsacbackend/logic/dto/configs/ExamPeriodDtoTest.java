package cr.co.ctpcit.citsacbackend.logic.dto.configs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class ExamPeriodDtoTest {
  @Autowired
  private JacksonTester<ExamPeriodDto> json;

  @Test
  void serializeJson() {

  }
}
