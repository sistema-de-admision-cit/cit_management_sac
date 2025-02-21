package cr.co.ctpcit.citsacbackend.rest.inscriptions;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_CLASS;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"delete-inscription.sql"}, executionPhase = AFTER_TEST_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InscriptionsControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @Order(1)
  void shouldReturnEnrollmentsByStudentId() {
    //Request
    ResponseEntity<String> response =
        restTemplate.getForEntity("/api/inscriptions/200123654", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());

    int enrollmentCount = documentContext.read("$.length()");
    assertThat(enrollmentCount).isEqualTo(2);

    JSONArray statuses = documentContext.read("$..status");
    assertThat(statuses).containsExactlyInAnyOrder("PENDING", "ELIGIBLE");
  }

  @Test
  @Order(2)
  void shouldReturnEnrollmentsByValueAsIdNumber() {
    //Request
    ResponseEntity<String> response =
        restTemplate.getForEntity("/api/inscriptions/search?value=191", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());

    int enrollmentCount = documentContext.read("$.length()");
    assertThat(enrollmentCount).isEqualTo(1);

    JSONArray statuses = documentContext.read("$..status");
    assertThat(statuses).containsExactlyInAnyOrder("REJECTED");
  }

  @Test
  @Order(3)
  void shouldReturnEnrollmentsByValueAsFirstName() {
    //Request
    ResponseEntity<String> response =
        restTemplate.getForEntity("/api/inscriptions/search?value=Valeria", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());

    int enrollmentCount = documentContext.read("$.length()");
    assertThat(enrollmentCount).isEqualTo(1);

    JSONArray statuses = documentContext.read("$..status");
    assertThat(statuses).containsExactlyInAnyOrder("INELIGIBLE");
  }

}
