package cr.co.ctpcit.citsacbackend.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    //@Test
    void getInscriptionById_shouldReturnOneInscriptionById() throws Exception {
        // Act
        MvcResult mvcResult = mockMvc.perform(get("/api/inscriptions/1"))
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

    //@Test
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
    void createInscription_shouldCreateOneInscription() {
    }
}