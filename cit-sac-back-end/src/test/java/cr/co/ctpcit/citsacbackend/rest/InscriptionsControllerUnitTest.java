package cr.co.ctpcit.citsacbackend.rest;

import cr.co.ctpcit.citsacbackend.data.enums.*;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.EnrollmentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.InscriptionDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.ParentsGuardianDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for the {@link InscriptionsController} class.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InscriptionsControllerUnitTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void testGetAllInscriptions() {
        // Define the expected result (list of InscriptionDto)
        InscriptionDto[] expectedInscriptions = new InscriptionDto[] {
                InscriptionDto.builder()
                        .student(new StudentDto("Michael", "Doe", "Smith", LocalDate.of(2010, 5, 10),
                                IdType.CC, "321654987", "Springfield High", false))
                        .parentGuardian(new ParentsGuardianDto("John", "Doe", "Smith",
                                IdType.CC, "123456789", "3001234567", "john.doe@example.com",
                                "123 Main St", Relationship.F))
                        .enrollment(new EnrollmentDto(ProcessStatus.P, Instant.now(),
                                Grades.FIFTH, KnownThrough.SM, LocalDate.of(2024, 6, 15),
                                true, true))
                        .build(),
                // Add more expected InscriptionDto as needed...
        };

        // Call the API
        ResponseEntity<InscriptionDto[]> response = testRestTemplate.getForEntity("/api/inscriptions", InscriptionDto[].class);

        // Verify the response status code
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify the response body
        InscriptionDto[] actualInscriptions = response.getBody();
        assertNotNull(actualInscriptions);
        assertArrayEquals(expectedInscriptions, actualInscriptions);
    }

    @Test
    public void testGetInscriptionById() {
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
        assertThat(inscription.parentGuardian()).isNotNull();
        assertThat(inscription.enrollment()).isNotNull();

        // Additional assertions to verify the values returned
        assertThat(inscription.student().firstName()).isEqualTo("Michael");
        assertThat(inscription.parentGuardian().firstName()).isEqualTo("John");
        assertThat(inscription.enrollment().status()).isEqualTo(ProcessStatus.P);
    }
}