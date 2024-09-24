package cr.co.ctpcit.citsacbackend.rest.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.rest.inscriptions.InscriptionsController;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for the {@link InscriptionsController} class.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class InscriptionsControllerUnitTests {
  @Autowired
  private TestRestTemplate testRestTemplate;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @Order(1)
  public void testGetInscriptionById_shouldReturnOneInscriptionById() {
    // Arrange
    ResponseEntity<String> response =
        testRestTemplate.getForEntity("/api/inscriptions/1", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    DocumentContext documentContext = JsonPath.parse(response.getBody());
    String id = documentContext.read("$.idNumber");
    assertThat(id).isNotNull();
    assertThat(id).isEqualTo("200123654");
    System.out.println(documentContext.jsonString());
  }

  @Test
  @Order(2)
  public void testGetAllInscriptions_shouldReturnAllInscriptions() {
    // Arrange
    String url = "/api/inscriptions?page=0&size=10&sort=idNumber,asc";

    // Act
    ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();

    DocumentContext documentContext = JsonPath.parse(response.getBody());
    int size = documentContext.read("$.length()");
    assertThat(size).isGreaterThan(2);
    String firstName = documentContext.read("$[0].firstName");
    assertThat(firstName).isEqualTo("Lucía");
  }

  @Test
  @Order(3)
  public void testGetInscriptionsByValue_shouldReturnInscriptionsByValue() {
    // Arrange
    ResponseEntity<String> response =
        testRestTemplate.getForEntity("/api/inscriptions/search?value=Rodriguez", String.class);

    // Assert
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    DocumentContext documentContext = JsonPath.parse(response.getBody());
    int size = documentContext.read("$.length()");
    assertThat(size).isEqualTo(2);
    String firstName = documentContext.read("$[0].firstName");
    assertThat(firstName).isEqualTo("Andrés");
  }

  @Test
  @Order(4)
  void updateExamDate_shouldReturnUpdatedStudent_whenValidIdAndDateProvided() throws Exception {
    String enrollmentId = "1";  // Use a valid enrollment ID from your test database
    String newExamDate = "2024-02-15"; // New exam date to update

    mockMvc.perform(MockMvcRequestBuilders.put("/api/inscriptions/{id}/exam", enrollmentId)
            .queryParam("date", newExamDate).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$.enrollments[0].id").value(enrollmentId))
        .andExpect(jsonPath("$.enrollments[0].examDate").value(
            newExamDate));  // Check if the exam date is updated
  }

  @Test
  @Order(5)
  void updateExamDate_shouldReturnNotFound_whenInvalidIdProvided() throws Exception {
    String invalidId = "999";  // Use an invalid ID that doesn't exist in the test database
    String newExamDate = "2024-02-15";

    mockMvc.perform(MockMvcRequestBuilders.put("/api/enrollment/{id}/exam", invalidId)
            .param("date", newExamDate).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  @Order(6)
  void updateStatus_shouldReturnNotFound_whenInvalidIdProvided() throws Exception {
    String invalidId = "999";  // Use an invalid ID that doesn't exist in the test database
    ProcessStatus newStatus = ProcessStatus.A;

    mockMvc.perform(MockMvcRequestBuilders.put("/api/enrollment/{id}/status", invalidId)
            .param("date", newStatus.toString()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}
