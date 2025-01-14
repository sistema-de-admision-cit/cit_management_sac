package cr.co.ctpcit.citsacbackend.rest.inscriptions;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"delete-inscription.sql"}, executionPhase = AFTER_TEST_CLASS)
class InscriptionFormControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @Sql(scripts = {"delete-inscription.sql"}, executionPhase = BEFORE_TEST_METHOD)
  void createInscription() throws Exception {
    MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();

    //Main request headers
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);

    //Body attachment headers
    HttpHeaders bodyAttachmentHeaders = new HttpHeaders();
    bodyAttachmentHeaders.setContentType(MediaType.APPLICATION_JSON);

    //Body attachment
    String body = Files.readString(Paths.get(
            "src/test/resources/cr/co/ctpcit/citsacbackend/rest/inscriptions/InscriptionJsonRequest.json"),
        StandardCharsets.UTF_8);

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
    HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(multipartRequest, headers);

    ResponseEntity<Void> response =
        restTemplate.postForEntity("/api/inscription/add", request, Void.class);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }
}
