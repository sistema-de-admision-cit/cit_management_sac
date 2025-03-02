package cr.co.ctpcit.citsacbackend.rest.configs;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.UpdateContactInfoConfigsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.UpdateWeightsConfigsDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_CLASS;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"systemConfigsRollback.sql"}, executionPhase = AFTER_TEST_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SystemConfigControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void getProcessWeights() {
    ResponseEntity<String> response =
        restTemplate.getForEntity("/api/system-config/get-process-weights", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());

    int enrollmentCount = documentContext.read("$.length()");
    assertThat(enrollmentCount).isEqualTo(3);
  }

  @Test
  void updateProcessWeights() {
    UpdateWeightsConfigsDto updateWeightsConfigsDto = new UpdateWeightsConfigsDto(0.3, 0.3, 0.4);

    HttpEntity<UpdateWeightsConfigsDto> request = new HttpEntity<>(updateWeightsConfigsDto);
    ResponseEntity<String> response =
        restTemplate.exchange("/api/system-config/update-process-weights",
            org.springframework.http.HttpMethod.PUT, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void getContactInfo() {
    ResponseEntity<String> response =
        restTemplate.getForEntity("/api/system-config/get-contact-info", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());

    int enrollmentCount = documentContext.read("$.length()");
    assertThat(enrollmentCount).isEqualTo(6);
  }

  @Test
  void updateContactInfo() {
    UpdateContactInfoConfigsDto updateContactInfoConfigsDto =
        new UpdateContactInfoConfigsDto("emailContact@cit.co.cr",
            "emailNotificationsContact@cit.co.cr", "88556396", "22276565", "CitEsGrande",
            "CitNoEsGrande");

    HttpEntity<UpdateContactInfoConfigsDto> request = new HttpEntity<>(updateContactInfoConfigsDto);
    ResponseEntity<String> response =
        restTemplate.exchange("/api/system-config/update-contact-info",
            org.springframework.http.HttpMethod.PUT, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void getCurrentExamPeriods() {
    ResponseEntity<String> response =
        restTemplate.getForEntity("/api/system-config/get-current-exam-periods", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());

    int enrollmentCount = documentContext.read("$.length()");
    assertThat(enrollmentCount).isEqualTo(3);
  }
}
