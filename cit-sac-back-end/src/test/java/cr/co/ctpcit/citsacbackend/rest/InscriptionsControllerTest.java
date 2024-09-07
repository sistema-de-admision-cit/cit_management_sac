package cr.co.ctpcit.citsacbackend.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.data.enums.ProcessStatus;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.InscriptionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

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
    void getInscriptions_shouldReturnAllInscriptions() throws Exception {
        // Perform the GET request to the /api/inscriptions endpoint
        MvcResult mvcResult = mockMvc.perform(get("/api/inscriptions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Parse the response to an array of InscriptionDto
        String jsonResponse = mvcResult.getResponse().getContentAsString();
        InscriptionDto[] inscriptions = objectMapper.readValue(jsonResponse, InscriptionDto[].class);

        // Assertions to verify the response is not empty and correct
        assertThat(inscriptions).isNotEmpty();
        assertThat(inscriptions[0].student().firstName()).isNotBlank();
        assertThat(inscriptions[0].parentGuardian().firstName()).isNotBlank();
        assertThat(inscriptions[0].enrollment().status()).isNotNull();
    }

    @Test
    void createInscription_shouldCreateOneInscription() {
    }

    @Test
    void getInscriptionById_shouldReturnOneInscriptionById() throws Exception {
        // Act
        MvcResult mvcResult = mockMvc.perform(get("/api/inscriptions/1"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();

        // Deserialize JSON response to InscriptionDto
        InscriptionDto responseDto = objectMapper.readValue(jsonResponse, InscriptionDto.class);

        // Assert
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.student()).isNotNull();
        assertThat(responseDto.student().firstName()).isEqualTo("Michael");
        assertThat(responseDto.student().firstSurname()).isEqualTo("Doe");
        assertThat(responseDto.parentGuardian()).isNotNull();
        assertThat(responseDto.enrollment()).isNotNull();
        assertThat(responseDto.enrollment().status()).isEqualTo(ProcessStatus.P);
    }
}