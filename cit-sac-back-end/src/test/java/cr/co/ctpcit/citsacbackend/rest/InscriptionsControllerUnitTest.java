package cr.co.ctpcit.citsacbackend.rest;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import cr.co.ctpcit.citsacbackend.data.enums.*;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.AddressDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.EnrollmentDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.ParentsGuardianDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.StudentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
        ResponseEntity<String> response = testRestTemplate.getForEntity("/api/inscriptions/603660526", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        String id = documentContext.read("$.idNumber");
        assertThat(id).isNotNull();
        assertThat(id).isEqualTo("603660526");
        System.out.println(documentContext.jsonString());
    }

    @Test
    public void testGetInscriptionById_shouldFailDueToInvalidId() {
        // Arrange
        ResponseEntity<String> response = testRestTemplate.getForEntity("/api/inscriptions/1234", String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("getInscriptionById.id: el tamaño del id debe ser entre 9 y 20 caracteres");
    }

    @Test
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
        assertThat(size).isEqualTo(5);
        String firstName = documentContext.read("$[0].firstName");
        assertThat(firstName).isEqualTo("Lucia");
    }

    @Test
    public void testGetInscriptionsByValue_shouldReturnInscriptionsByValue() {
        // Arrange
        ResponseEntity<String> response = testRestTemplate.getForEntity("/api/inscriptions/search?value=Martinez", String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int size = documentContext.read("$.length()");
        assertThat(size).isEqualTo(2);
        String firstName = documentContext.read("$[0].firstName");
        assertThat(firstName).isEqualTo("Pedro");
    }

    @Test
    public void testCreateInscription_shouldCreateANewInscription() {
        // Arrange
        // Create sample enrollment
        StudentDto body = getStudentDto();

        // Act
        ResponseEntity<Void> response = testRestTemplate.postForEntity("/api/inscriptions/add", body, Void.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        //Assert URI Location header
        URI location = response.getHeaders().getLocation();
        ResponseEntity<String> responseGet = testRestTemplate.getForEntity(location, String.class);
        assertThat(responseGet.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    private static StudentDto getStudentDto() {
        EnrollmentDto enrollmentDto = new EnrollmentDto(
                null,
                ProcessStatus.P,
                LocalDateTime.of(2024, 9, 6, 12, 0),
                Grades.FORTH,
                KnownThrough.FM,
                LocalDate.of(2024, 10, 1),
                true,
                true
        );

        ParentsGuardianDto parentGuardianDto = new ParentsGuardianDto(
                null,
                "Alice",
                "Williams",
                "Davis",
                IdType.CC,  // Sample enum for idType
                "159753486",
                "3129876543",
                "alice.williams@gmail.com",
                Relationship.M,  // Sample enum for relationship
                List.of(AddressDto
                        .builder()
                        .addressInfo("Calle 4A, Casa 17")
                        .city("Heredia")
                        .district("San Francisco")
                        .province("Heredia")
                        .country("Costa Rica")
                        .build())  // Empty list of addresses
        );
        return new StudentDto(
                null,
                "Lucas",
                "Johnson",
                "Taylor",
                LocalDate.of(2011, 5, 12),
                IdType.DI,
                "951357852",
                "Springfield Academy",
                false,
                List.of(enrollmentDto),
                List.of(parentGuardianDto)
        );
    }
}