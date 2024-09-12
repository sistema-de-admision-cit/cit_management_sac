package cr.co.ctpcit.citsacbackend.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the {@link InscriptionsController} class.
 */

@SpringBootTest
@AutoConfigureMockMvc
class InscriptionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getInscriptionById_shouldReturnOneInscriptionById() throws Exception {
        // Act
        MvcResult mvcResult = mockMvc.perform(get("/api/inscriptions/321654987"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();

        // Deserialize JSON response to InscriptionDto
        StudentDto responseDto = objectMapper.readValue(jsonResponse, StudentDto.class);

        // Assert
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.firstName()).isEqualTo("Michael");
        assertThat(responseDto.parents().getFirst().firstName()).isEqualTo("John");
        assertThat(responseDto.enrollments().getFirst().status()).isEqualTo(ProcessStatus.P);
    }

    @Test
    void getInscriptions_shouldReturnAllInscriptions() throws Exception {
        // Perform the GET request to the /api/inscriptions endpoint
        MvcResult mvcResult = mockMvc.perform(get("/api/inscriptions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Parse the response to an array of InscriptionDto
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        StudentDto[] inscriptions = objectMapper.readValue(jsonResponse, StudentDto[].class);

        // Assertions to verify the response is not empty and correct
        assertThat(inscriptions).isNotEmpty();
        assertThat(inscriptions[0].firstName()).isNotBlank();
        assertThat(inscriptions[0].parents().getFirst().firstName()).isNotBlank();
        assertThat(inscriptions[0].enrollments().getFirst().status()).isEqualTo(ProcessStatus.P);
    }

    //@Test
    void postInscriptions_shouldCreateNewEnrollment() throws Exception {
        // Create a JSON payload for the request
        String studentJson = """
        {
          "id": null,
          "firstName": "Alice",
          "firstSurname": "Green",
          "secondSurname": "Taylor",
          "birthDate": "2010-06-12",
          "idType": "CC",
          "idNumber": "406980444",
          "previousSchool": "Sunrise Elementary",
          "hasAccommodations": false,
          "parents": [
            {
              "id": null,
              "firstName": "Jane",
              "firstSurname": "Green",
              "secondSurname": "Johnson",
              "idType": "CC",
              "idNumber": "3344556677",
              "phoneNumber": "3201122334",
              "email": "jane.green@example.com",
              "homeAddress": "123 Palm St",
              "relationship": "M"
            },
            {
              "id": null,
              "firstName": "Mark",
              "firstSurname": "Green",
              "secondSurname": "Smith",
              "idType": "CC",
              "idNumber": "4455667788",
              "phoneNumber": "3102233445",
              "email": "mark.green@example.com",
              "homeAddress": "456 Oak St",
              "relationship": "F"
            }
          ],
          "enrollments": [
            {
              "id": null,
              "status": "P",
              "enrollmentDate": "2024-09-06T10:00:00",
              "gradeToEnroll": "3",
              "knownThrough": "SM",
              "examDate": "2024-09-15",
              "consentGiven": true,
              "whatsappNotification": false
            }
          ],
        }
        """;

        mockMvc.perform(post("/api/inscriptions/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.enrollments[0].status").value("P"))
                .andExpect(jsonPath("$.parents[0].firstName").value("Jane"))
                .andExpect(jsonPath("$.parents[1].firstName").value("Mark"));
    }

}