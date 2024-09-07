package cr.co.ctpcit.citsacbackend.rest;

import cr.co.ctpcit.citsacbackend.data.enums.*;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.InscriptionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the {@link InscriptionsController} class.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InscriptionsControllerUnitTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testGetInscriptionById_shouldReturnOneInscriptionById() {
        // Arrange
        Long inscriptionId = 1L;
        String url = "/api/inscriptions/" + inscriptionId;

        // Act
        ResponseEntity<InscriptionDto> response = testRestTemplate.getForEntity(url, InscriptionDto.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        InscriptionDto inscription = response.getBody();
        assertThat(inscription.student()).isNotNull();
        assertThat(inscription.parents()).isNotNull();
        assertThat(inscription.enrollments()).isNotNull();

        // Additional assertions to verify the values returned
        assertThat(inscription.student().firstName()).isEqualTo("Michael");
        assertThat(inscription.parents().getFirst().firstName()).isEqualTo("John");
        assertThat(inscription.enrollments().getFirst().status()).isEqualTo(ProcessStatus.P);
    }

    @Test
    public void testGetAllInscriptions_shouldReturnAllInscriptions() {
        // Arrange
        String url = "/api/inscriptions";

        // Act
        ResponseEntity<InscriptionDto[]> response = testRestTemplate.getForEntity(url, InscriptionDto[].class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
    }
}