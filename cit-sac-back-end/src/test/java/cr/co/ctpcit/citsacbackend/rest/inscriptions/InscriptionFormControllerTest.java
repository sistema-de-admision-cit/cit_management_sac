package cr.co.ctpcit.citsacbackend.rest.inscriptions;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InscriptionFormControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @Order(1)
  @Sql(scripts = {"delete-inscription.sql"}, executionPhase = BEFORE_TEST_METHOD)
  void createInscription() throws Exception {
    HttpEntity<MultiValueMap<String, Object>> request =
        createRequest("InscriptionNonexistentStudentJsonRequest.json");

    ResponseEntity<Void> response =
        restTemplate.postForEntity("/api/inscription/add", request, Void.class);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  @Order(2)
  @Sql(scripts = {"delete-inscription.sql"}, executionPhase = BEFORE_TEST_METHOD)
  void createInscriptionWithExistentDad() throws IOException {
    HttpEntity<MultiValueMap<String, Object>> request =
        createRequest("InscriptionExistingDadJsonRequest.json");

    ResponseEntity<Void> response =
        restTemplate.postForEntity("/api/inscription/add", request, Void.class);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  @Sql(scripts = {"delete-inscription.sql"}, executionPhase = AFTER_TEST_METHOD)
  void createInscriptionForExistentStudent_NonexistentDad() throws IOException {
    HttpEntity<MultiValueMap<String, Object>> request =
        createRequest("InscriptionExistentStudentNonexistentDadJsonRequest.json");

    ResponseEntity<Void> response =
        restTemplate.postForEntity("/api/inscription/add", request, Void.class);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  void createInscriptionWithInvalidData() {
    // Implementar caso para datos inválidos
  }

  private HttpEntity<MultiValueMap<String, Object>> createRequest(String jsonRequest)
      throws IOException {
    MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    HttpHeaders bodyAttachmentHeaders = new HttpHeaders();
    bodyAttachmentHeaders.setContentType(MediaType.APPLICATION_JSON);

    String body = Files.readString(Paths.get(
        String.format("src/test/resources/cr/co/ctpcit/citsacbackend/rest/inscriptions/%s",
            jsonRequest)), StandardCharsets.UTF_8);

    HttpEntity<String> bodyAttachment = new HttpEntity<>(body, bodyAttachmentHeaders);

    HttpHeaders fileAttachmentHeaders = new HttpHeaders();
    fileAttachmentHeaders.setContentType(MediaType.APPLICATION_PDF);

    ByteArrayResource grades = new ByteArrayResource("Some file content".getBytes()) {
      @Override
      public String getFilename() {
        return "grades.pdf";
      }
    };

    HttpEntity<ByteArrayResource> fileAttachment = new HttpEntity<>(grades, fileAttachmentHeaders);

    multipartRequest.add("inscription", bodyAttachment);
    multipartRequest.add("grades", fileAttachment);

    return new HttpEntity<>(multipartRequest, headers);
  }

  @AfterAll
  static void cleanUp() {
    String location = "D:/temp/spring/uploads/";
    try {
      Files.walk(Paths.get(location)).filter(Files::isRegularFile).map(Path::toFile)
          .forEach(File::delete);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}


