package cr.co.ctpcit.citsacbackend.rest.inscriptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentUpdateDto;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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

  @Test
  @Order(4)
  void findAllEnrollmentsPageable() {
    //Request
    ResponseEntity<String> response =
        restTemplate.getForEntity("/api/inscriptions?page=0&size=10", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());

    int enrollmentCount = documentContext.read("$.length()");
    assertThat(enrollmentCount).isEqualTo(10);
  }

  @Test
  @Order(5)
  void shouldUpdateExamDateOnEnrollmentById() {
    //Get enrollments of person with id 200123654
    ResponseEntity<String> response =
        restTemplate.getForEntity("/api/inscriptions/200123654", String.class);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    Integer enrollmentId = documentContext.read("$[0].id");

    EnrollmentUpdateDto updatedEnrollment =
        new EnrollmentUpdateDto(LocalDate.parse("2025-02-28"), null, null);

    //Request
    HttpEntity<EnrollmentUpdateDto> request = new HttpEntity<>(updatedEnrollment);
    ResponseEntity<Void> putResponse =
        restTemplate.exchange("/api/inscriptions/update-exam-date/{id}", HttpMethod.PUT, request,
            Void.class, enrollmentId);

    assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    updatedEnrollment = new EnrollmentUpdateDto(LocalDate.parse("2024-01-10"), null, null);
    request = new HttpEntity<>(updatedEnrollment);
    putResponse =
        restTemplate.exchange("/api/inscriptions/update-exam-date/{id}", HttpMethod.PUT, request,
            Void.class, enrollmentId);

    assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  @Order(6)
  void shouldUpdateStatusOnEnrollmentById() {
    //Get enrollments of person with id 200123654
    ResponseEntity<String> response =
        restTemplate.getForEntity("/api/inscriptions/200123654", String.class);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    Integer enrollmentId = documentContext.read("$[0].id");

    EnrollmentUpdateDto updatedEnrollment = new EnrollmentUpdateDto(null, ProcessStatus.REJECTED, null);

    //Request
    HttpEntity<EnrollmentUpdateDto> request = new HttpEntity<>(updatedEnrollment);
    ResponseEntity<Void> putResponse =
        restTemplate.exchange("/api/inscriptions/update-status/{id}", HttpMethod.PUT, request,
            Void.class, enrollmentId);

    assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    updatedEnrollment = new EnrollmentUpdateDto(null, ProcessStatus.PENDING, null);
    request = new HttpEntity<>(updatedEnrollment);
    putResponse =
        restTemplate.exchange("/api/inscriptions/update-status/{id}", HttpMethod.PUT, request,
            Void.class, enrollmentId);

    assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  @Order(7)
  void shouldChangeWhatsappPermissionOnEnrollmentById() {
    //Get enrollments of person with id 200123654
    ResponseEntity<String> response =
        restTemplate.getForEntity("/api/inscriptions/200123654", String.class);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    Integer enrollmentId = documentContext.read("$[0].id");

    EnrollmentUpdateDto updatedEnrollment = new EnrollmentUpdateDto(null, null, false);

    //Request
    HttpEntity<EnrollmentUpdateDto> request = new HttpEntity<>(updatedEnrollment);
    ResponseEntity<Void> putResponse =
        restTemplate.exchange("/api/inscriptions/update-whatsapp/{id}", HttpMethod.PUT, request,
            Void.class, enrollmentId);

    assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);


    updatedEnrollment = new EnrollmentUpdateDto(null, null, true);

    request = new HttpEntity<>(updatedEnrollment);
    putResponse =
        restTemplate.exchange("/api/inscriptions/update-whatsapp/{id}", HttpMethod.PUT, request,
            Void.class, enrollmentId);

    assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

}
