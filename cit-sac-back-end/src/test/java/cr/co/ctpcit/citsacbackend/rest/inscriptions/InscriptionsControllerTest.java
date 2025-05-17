package cr.co.ctpcit.citsacbackend.rest.inscriptions;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import cr.co.ctpcit.citsacbackend.TestConfig;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentUpdateDto;
import cr.co.ctpcit.citsacbackend.logic.services.notifs.NotificationsService;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_CLASS;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"rollback-update-inscriptions.sql"}, executionPhase = AFTER_TEST_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(TestConfig.class)
class InscriptionsControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @MockitoBean
  private NotificationsService notificationsService;  // This will mock the actual service

  @Autowired
  private HttpEntity<String> getHttpEntity;

  @Autowired
  private HttpHeaders getBearerHeader;

  @Autowired
  private String generateTestToken;

  @Test
  @Order(1)
  void shouldReturnEnrollmentsByStudentId() {
    //Request
    ResponseEntity<String> response =
        restTemplate.exchange("/api/inscriptions/200123654", HttpMethod.GET, getHttpEntity, String.class);

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
        restTemplate.exchange("/api/inscriptions/search?value=165", HttpMethod.GET, getHttpEntity, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());

    int enrollmentCount = documentContext.read("$.length()");
    assertThat(enrollmentCount).isEqualTo(1);

    JSONArray statuses = documentContext.read("$..idNumber");
    assertThat(statuses).containsExactlyInAnyOrder("201654987", "900654321");
  }

  @Test
  @Order(3)
  void shouldReturnEnrollmentsByValueAsFirstName() {
    //Request
    ResponseEntity<String> response =
        restTemplate.exchange("/api/inscriptions/search?value=Valeria", HttpMethod.GET, getHttpEntity, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());

    int enrollmentCount = documentContext.read("$.length()");
    assertThat(enrollmentCount).isEqualTo(2);

    JSONArray id = documentContext.read("$..idNumber");
    assertThat(id).containsExactlyInAnyOrder("270456789", "940123789", "201234025", "970456123");
  }

  @Test
  @Order(4)
  void findAllEnrollmentsPageable() {
    //Request
    ResponseEntity<String> response =
        restTemplate.exchange("/api/inscriptions?page=0&size=10", HttpMethod.GET, getHttpEntity, String.class);

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
        restTemplate.exchange("/api/inscriptions/200123654", HttpMethod.GET, getHttpEntity, String.class);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    Integer enrollmentId = documentContext.read("$[0].id");

    EnrollmentUpdateDto updatedEnrollment =
        new EnrollmentUpdateDto(LocalDate.parse("2025-02-28"), null, null,new BigDecimal("8.5"), null, null);

    //Request
    HttpEntity<EnrollmentUpdateDto> request = new HttpEntity<>(updatedEnrollment, getBearerHeader);
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
        restTemplate.exchange("/api/inscriptions/200123654", HttpMethod.GET, getHttpEntity, String.class);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    Integer enrollmentId = documentContext.read("$[0].id");

    EnrollmentUpdateDto updatedEnrollment =
        new EnrollmentUpdateDto(null, ProcessStatus.REJECTED, null, new BigDecimal("8.5"),null,null);

    //Request
    HttpEntity<EnrollmentUpdateDto> request = new HttpEntity<>(updatedEnrollment, getBearerHeader);
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
        restTemplate.exchange("/api/inscriptions/200123654", HttpMethod.GET, getHttpEntity, String.class);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    Integer enrollmentId = documentContext.read("$[0].id");

    EnrollmentUpdateDto updatedEnrollment = new EnrollmentUpdateDto(null, null, false,new BigDecimal("8.5"), null, null);

    //Request
    HttpEntity<EnrollmentUpdateDto> request = new HttpEntity<>(updatedEnrollment, getBearerHeader);
    ResponseEntity<Void> putResponse =
        restTemplate.exchange("/api/inscriptions/update-whatsapp/{id}", HttpMethod.PUT, request,
            Void.class, enrollmentId);

    assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  @Order(8)
  @Sql(scripts = {"rollback-update-inscriptions.sql"}, executionPhase = BEFORE_TEST_METHOD)
  void shouldUpdateEnrollmentSavingCommentsAndActionPerformerLog() {

    doNothing().when(notificationsService).createEmailForEnrollmentUpdate(anyLong(), any(EnrollmentUpdateDto.class));


    //Get enrollments of person with id 200123654
    ResponseEntity<String> response =
        restTemplate.exchange("/api/inscriptions/200123654", HttpMethod.GET, getHttpEntity, String.class);

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    Integer enrollmentId = documentContext.read("$[0].id");

    EnrollmentUpdateDto updatedEnrollment =
        new EnrollmentUpdateDto(LocalDate.parse("2025-02-28"), ProcessStatus.REJECTED, false,new BigDecimal("8.5"),
            "Action made to update enrollment as test", "sysadmin@cit.co.cr");

    //Request
    HttpEntity<EnrollmentUpdateDto> request = new HttpEntity<>(updatedEnrollment, getBearerHeader);
    ResponseEntity<Void> putResponse =
        restTemplate.exchange("/api/inscriptions/update-enrollment/{id}", HttpMethod.PUT, request,
            Void.class, enrollmentId);

    assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    verify(notificationsService, times(1))
            .createEmailForEnrollmentUpdate(eq(Long.valueOf(enrollmentId)), eq(updatedEnrollment));
  }

  static String testDocumentLocation;

  @Test
  @Order(9)
  public void testUploadDocument_Success() {
    //Request
    HttpEntity<MultiValueMap<String, Object>> request = createFileUploadEndpointRequest();

    ResponseEntity<Void> response =
        restTemplate.postForEntity("/api/inscriptions/documents/upload", request, Void.class);

    //Get the document location
    testDocumentLocation = Objects.requireNonNull(response.getHeaders().getLocation()).toString();

    assertEquals(HttpStatus.CREATED, response.getStatusCode());


  }

  @Test
  @Order(10)
  @DirtiesContext
  public void testDownloadDocuments_Success() {
    //Get id from the location
    long documentId =
        Long.parseLong(testDocumentLocation.substring(testDocumentLocation.lastIndexOf("/") + 1));

    ResponseEntity<byte[]> response =
        restTemplate.exchange("/api/inscriptions/documents/download/" + documentId, HttpMethod.GET,
            getHttpEntity, byte[].class);

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
        HttpMethod.DELETE, getHttpEntity, Void.class);

    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
  }

  private HttpEntity<MultiValueMap<String, Object>> createFileUploadEndpointRequest() {
    MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();

    //Main request headers
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    headers.setBearerAuth(generateTestToken);

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
