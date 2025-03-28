package cr.co.ctpcit.citsacbackend.rest.inscriptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentUpdateDto;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_CLASS;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"rollback-update-inscriptions.sql"}, executionPhase = AFTER_TEST_CLASS)
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
        restTemplate.getForEntity("/api/inscriptions/search?value=165", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());

    int enrollmentCount = documentContext.read("$.length()");
    assertThat(enrollmentCount).isEqualTo(1);

    JSONArray statuses = documentContext.read("$..status");
    assertThat(statuses).containsExactlyInAnyOrder("PENDING");
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

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    int enrollmentCount = documentContext.read("$.length()");

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
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
        new EnrollmentUpdateDto(LocalDate.parse("2025-02-28"), null, null, null, null);

    //Request
    HttpEntity<EnrollmentUpdateDto> request = new HttpEntity<>(updatedEnrollment);
    ResponseEntity<Void> putResponse =
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

    EnrollmentUpdateDto updatedEnrollment =
        new EnrollmentUpdateDto(null, ProcessStatus.REJECTED, null, null, null);

    //Request
    HttpEntity<EnrollmentUpdateDto> request = new HttpEntity<>(updatedEnrollment);
    ResponseEntity<Void> putResponse =
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

    EnrollmentUpdateDto updatedEnrollment = new EnrollmentUpdateDto(null, null, false, null, null);

    //Request
    HttpEntity<EnrollmentUpdateDto> request = new HttpEntity<>(updatedEnrollment);
    ResponseEntity<Void> putResponse =
        restTemplate.exchange("/api/inscriptions/update-whatsapp/{id}", HttpMethod.PUT, request,
            Void.class, enrollmentId);

    assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  @Order(8)
  @Sql(scripts = {"rollback-update-inscriptions.sql"}, executionPhase = BEFORE_TEST_METHOD)
  void shouldUpdateEnrollmentSavingCommentsAndActionPerformerLog() {
    //Get enrollments of person with id 200123654
    ResponseEntity<String> response =
        restTemplate.getForEntity("/api/inscriptions/200123654", String.class);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    Integer enrollmentId = documentContext.read("$[0].id");

    EnrollmentUpdateDto updatedEnrollment =
        new EnrollmentUpdateDto(LocalDate.parse("2025-02-28"), ProcessStatus.REJECTED, false,
            "Action made to update enrollment as test", 1);

    //Request
    HttpEntity<EnrollmentUpdateDto> request = new HttpEntity<>(updatedEnrollment);
    ResponseEntity<Void> putResponse =
        restTemplate.exchange("/api/inscriptions/update-enrollment/{id}", HttpMethod.PUT, request,
            Void.class, enrollmentId);

    assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  ByteArrayResource fileContent = new ByteArrayResource("Dummy file content".getBytes()) {
    @Override
    public String getFilename() {
      return "test.pdf";
    }
  };
  static String testDocumentLocation;

  @Test
  @Order(9)
  public void testUploadDocument_Success() throws Exception {
    //Request
    HttpEntity<MultiValueMap<String, Object>> request = createFileUploadEndpointRequest();

    ResponseEntity<Void> response =
        restTemplate.postForEntity("/api/inscriptions/documents/upload", request, Void.class);

    //Get the document location
    testDocumentLocation = response.getHeaders().getLocation().toString();

    assertEquals(HttpStatus.CREATED, response.getStatusCode());


  }

  @Test
  @Order(10)
  @DirtiesContext
  public void testDownloadDocuments_Success() throws Exception {
    //Get id from the location
    long documentId =
        Long.parseLong(testDocumentLocation.substring(testDocumentLocation.lastIndexOf("/") + 1));

    ResponseEntity<byte[]> response =
        restTemplate.exchange("/api/inscriptions/documents/download/" + documentId, HttpMethod.GET,
            null, byte[].class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  @Order(11)
  public void testDeleteDocument_Success() {
    //Get id from the location

    long documentId =
        Long.parseLong(testDocumentLocation.substring(testDocumentLocation.lastIndexOf("/") + 1));

    ResponseEntity<Void> response = restTemplate.exchange("/api/inscriptions/documents/delete/" + documentId,
        HttpMethod.DELETE, null, Void.class);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  private HttpEntity<MultiValueMap<String, Object>> createFileUploadEndpointRequest()
      throws IOException {
    MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();

    //Main request headers
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    //File attachment headers
    HttpHeaders fileAttachmentHeaders = new HttpHeaders();
    fileAttachmentHeaders.setContentType(MediaType.APPLICATION_PDF);

    //File attachment
    ByteArrayResource someFile = new ByteArrayResource("Some file content".getBytes()) {
      @Override
      public String getFilename() {
        return "testFile.pdf";
      }
    };

    HttpEntity<ByteArrayResource> fileAttachment =
        new HttpEntity<>(someFile, fileAttachmentHeaders);

    multipartRequest.add("file", fileAttachment);
    multipartRequest.add("documentType", "OT");
    multipartRequest.add("enrollmentId", 2L);

    //Request
    return new HttpEntity<>(multipartRequest, headers);
  }
}
