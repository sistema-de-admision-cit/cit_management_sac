package cr.co.ctpcit.citsacbackend.rest.configs;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import cr.co.ctpcit.citsacbackend.TestConfig;
import cr.co.ctpcit.citsacbackend.TestProvider;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.ExamPeriodDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.UpdateContactInfoConfigsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.UpdateQuantityConfigsDto;
import cr.co.ctpcit.citsacbackend.logic.dto.configs.UpdateWeightsConfigsDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_CLASS;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"systemConfigsRollback.sql"}, executionPhase = AFTER_TEST_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(TestConfig.class)
class SystemConfigControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private HttpEntity<String> getHttpEntity;

  @Autowired
  private HttpHeaders getBearerHeader;

  @Test
  void getQuestionsQuantity() {
    ResponseEntity<String> response =
            restTemplate.exchange("/api/system-config/get-questions-quantity", HttpMethod.GET, getHttpEntity, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());

    int enrollmentCount = documentContext.read("$.length()");
    assertThat(enrollmentCount).isEqualTo(2);
  }

  @Test
  void updateQuestionsQuantity() {
    UpdateQuantityConfigsDto quantityConfigsDto = new UpdateQuantityConfigsDto(20,21);


    HttpEntity<UpdateQuantityConfigsDto> request = new HttpEntity<>(quantityConfigsDto, getBearerHeader);
    ResponseEntity<String> response =
            restTemplate.exchange("/api/system-config/update-questions-quantity",
                   PUT, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void getProcessWeights() {
    ResponseEntity<String> response =
        restTemplate.exchange("/api/system-config/get-process-weights", HttpMethod.GET, getHttpEntity, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());

    int enrollmentCount = documentContext.read("$.length()");
    assertThat(enrollmentCount).isEqualTo(2);
  }

  @Test
  void updateProcessWeights() {
    UpdateWeightsConfigsDto updateWeightsConfigsDto = new UpdateWeightsConfigsDto(0.3, 0.7);

    HttpEntity<UpdateWeightsConfigsDto> request = new HttpEntity<>(updateWeightsConfigsDto, getBearerHeader);
    ResponseEntity<String> response =
        restTemplate.exchange("/api/system-config/update-process-weights",
            PUT, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void getContactInfo() {
    ResponseEntity<String> response =
        restTemplate.exchange("/api/system-config/get-contact-info", HttpMethod.GET, getHttpEntity, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());

    int enrollmentCount = documentContext.read("$.length()");
    assertThat(enrollmentCount).isEqualTo(8);
  }

  @Test
  void updateContactInfo() {
    UpdateContactInfoConfigsDto updateContactInfoConfigsDto =
        new UpdateContactInfoConfigsDto("emailContact@cit.co.cr",
            "emailNotificationsContact@cit.co.cr", "88556396", "22276565", "CitEsGrande",
            "CitNoEsGrande","campus15","ApiKeyWhatsapp");

    HttpEntity<UpdateContactInfoConfigsDto> request = new HttpEntity<>(updateContactInfoConfigsDto, getBearerHeader);
    ResponseEntity<String> response =
        restTemplate.exchange("/api/system-config/update-contact-info",
            PUT, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void getExamPeriodById() {
    ResponseEntity<String> response =
        restTemplate.exchange("/api/system-config/get-exam-period/1", HttpMethod.GET, getHttpEntity, String.class);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    Integer enrollmentId = documentContext.read("$.id");

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(enrollmentId).isEqualTo(1);
  }

  @Test
  void getCurrentExamPeriods() {
    ResponseEntity<String> response =
        restTemplate.exchange("/api/system-config/get-current-exam-periods", HttpMethod.GET, getHttpEntity, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());

    int enrollmentCount = documentContext.read("$.length()");
    assertThat(enrollmentCount).isEqualTo(3);
  }

  @Test
  void getExamPeriodsByYear() {
    ResponseEntity<String> response =
        restTemplate.exchange("/api/system-config/get-exam-periods/2025", HttpMethod.GET, getHttpEntity, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    DocumentContext documentContext = JsonPath.parse(response.getBody());

    int enrollmentCount = documentContext.read("$.length()");
    assertThat(enrollmentCount).isEqualTo(3);
  }

  @Test
  void createExamPeriod() {
    ExamPeriodDto examPeriodDto = TestProvider.provideNonExistentExamPeriodDto();

    HttpEntity<ExamPeriodDto> request = new HttpEntity<>(examPeriodDto, getBearerHeader);
    ResponseEntity<String> response = restTemplate.exchange("/api/system-config/create-exam-period",
        HttpMethod.POST, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
  }

  @Test
  void createDuplicateExamPeriod_ShouldFail() {
    ExamPeriodDto examPeriodDto = TestProvider.provideExistentExamPeriodDto();

    HttpEntity<ExamPeriodDto> request = new HttpEntity<>(examPeriodDto, getBearerHeader);
    ResponseEntity<String> response = restTemplate.exchange("/api/system-config/create-exam-period",
        HttpMethod.POST, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
  }
}
