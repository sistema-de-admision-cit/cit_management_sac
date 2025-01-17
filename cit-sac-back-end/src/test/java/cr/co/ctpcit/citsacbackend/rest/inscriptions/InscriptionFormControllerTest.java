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
@Sql(scripts = {"delete-inscription.sql"}, executionPhase = AFTER_TEST_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InscriptionFormControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @Order(1)
  @Sql(scripts = {"delete-inscription.sql"}, executionPhase = BEFORE_TEST_METHOD)
  void createInscription() throws Exception {
    //Request
    HttpEntity<MultiValueMap<String, Object>> request =
        createRequest("InscriptionJsonRequest.json");

    ResponseEntity<Void> response =
        restTemplate.postForEntity("/api/inscription/add", request, Void.class);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  @Order(2)
  void createInscriptionWithExistentDad() throws IOException {
    //Request
    HttpEntity<MultiValueMap<String, Object>> request =
        createRequest("InscriptionSameDadJsonRequest.json");

    ResponseEntity<Void> response =
        restTemplate.postForEntity("/api/inscription/add", request, Void.class);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  void createInscriptionForExistentStudent() throws IOException {
    //Request for existent student different exam date
    HttpEntity<MultiValueMap<String, Object>> request =
        createRequest("InscriptionExistentStudentJsonRequest.json");

    ResponseEntity<Void> response =
        restTemplate.postForEntity("/api/inscription/add", request, Void.class);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());

    //Request for existent student same exam date
    request = createRequest("InscriptionJsonRequest.json");

    ResponseEntity<Void> response2 =
        restTemplate.postForEntity("/api/inscription/add", request, Void.class);

    assertEquals(HttpStatus.CONFLICT, response2.getStatusCode());
  }

  @Test
  void createInscriptionWithInvalidData() {

  }

  private HttpEntity<MultiValueMap<String, Object>> createRequest(String jsonRequest)
      throws IOException {
    MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();

    //Main request headers
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    //Body attachment headers
    HttpHeaders bodyAttachmentHeaders = new HttpHeaders();
    bodyAttachmentHeaders.setContentType(MediaType.APPLICATION_JSON);

    //Body attachment
    String body = Files.readString(Paths.get(
        String.format("src/test/resources/cr/co/ctpcit/citsacbackend/rest/inscriptions/%s",
            jsonRequest)), StandardCharsets.UTF_8);

    HttpEntity<String> bodyAttachment = new HttpEntity<>(body, bodyAttachmentHeaders);

    //File attachment headers
    HttpHeaders fileAttachmentHeaders = new HttpHeaders();
    fileAttachmentHeaders.setContentType(MediaType.APPLICATION_PDF);

    //File attachment
    ByteArrayResource grades = new ByteArrayResource("Some file content".getBytes()) {
      @Override
      public String getFilename() {
        return "grades.pdf";
      }
    };

    HttpEntity<ByteArrayResource> fileAttachment = new HttpEntity<>(grades, fileAttachmentHeaders);

    //Add body and file to the request
    multipartRequest.add("inscription", bodyAttachment);
    multipartRequest.add("grades", fileAttachment);

    //Request
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
