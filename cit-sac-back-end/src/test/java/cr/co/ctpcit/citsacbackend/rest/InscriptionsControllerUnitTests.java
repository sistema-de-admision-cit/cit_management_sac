package cr.co.ctpcit.citsacbackend.rest;

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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for the {@link InscriptionsController} class.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
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
        ResponseEntity<String> response = testRestTemplate.getForEntity("/api/inscriptions/1", String.class);

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
        ResponseEntity<String> response = testRestTemplate.getForEntity("/api/inscriptions/search?value=Rodriguez", String.class);

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
                        .queryParam("date", newExamDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.enrollments[0].id").value(enrollmentId))
                .andExpect(jsonPath("$.enrollments[0].examDate").value(newExamDate));  // Check if the exam date is updated
    }

    @Test
    @Order(5)
    void updateExamDate_shouldReturnNotFound_whenInvalidIdProvided() throws Exception {
        String invalidId = "999";  // Use an invalid ID that doesn't exist in the test database
        String newExamDate = "2024-02-15";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/enrollment/{id}/exam", invalidId)
                        .param("date", newExamDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(6)
    void updateStatus_shouldReturnUpdatedStudent_whenValidIdAndStatusProvided() throws Exception {
        String enrollmentId = "3";  // Use a valid enrollment ID from your test database
        ProcessStatus newStatus = ProcessStatus.I; // New exam date to update

        mockMvc.perform(MockMvcRequestBuilders.put("/api/inscriptions/{id}/status", enrollmentId)
                        .queryParam("status", newStatus.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.enrollments[0].id").value(enrollmentId))
                .andExpect(jsonPath("$.enrollments[0].status").value(newStatus.toString()));  // Check if the exam date is updated
    }

    @Test
    @Order(7)
    void updateStatus_shouldReturnNotFound_whenInvalidIdProvided() throws Exception {
        String invalidId = "999";  // Use an invalid ID that doesn't exist in the test database
        ProcessStatus newStatus = ProcessStatus.A;

        mockMvc.perform(MockMvcRequestBuilders.put("/api/enrollment/{id}/status", invalidId)
                        .param("date", newStatus.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(8)
    @Disabled
    public void testCreateInscription_shouldCreateANewInscription() throws IOException {
        // Arrange
        // Create sample enrollment
        String body = Files.readString(Paths.get("src/test/resources/inscriptionExpected.json"));

        //Create Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Act
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Void> response = testRestTemplate.postForEntity("/api/inscriptions/add", entity, Void.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        //Assert URI Location header
        URI location = response.getHeaders().getLocation();
        ResponseEntity<String> responseGet = testRestTemplate.getForEntity(location, String.class);
        assertThat(responseGet.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    @Order(9)
    @Disabled
    public void testCreateInscription_shouldFailDueToConflictEnrollment() throws IOException {
        // Arrange
        // Create sample enrollment
        String body = Files.readString(Paths.get("src/test/resources/inscriptionExpected.json"));

        //Create Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Act
        HttpEntity<String> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = testRestTemplate.postForEntity("/api/inscription/add", entity, String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody()).contains(
                "El estudiante ya tiene una inscripción para la fecha seleccionada. " +
                "Debe seleccionar otra fecha o comunicarse con el área de Servicio al Cliente."
        );
    }
}