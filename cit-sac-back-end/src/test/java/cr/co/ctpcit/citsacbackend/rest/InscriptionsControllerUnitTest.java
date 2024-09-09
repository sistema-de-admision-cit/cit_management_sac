package cr.co.ctpcit.citsacbackend.rest;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the {@link InscriptionsController} class.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InscriptionsControllerUnitTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    //@Test
    public void testGetInscriptionById_shouldReturnOneInscriptionById() {

    }

    @Test
    public void testGetAllInscriptions_shouldReturnAllInscriptions() {
        // Arrange
        String url = "/api/inscriptions";

        // Act
        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

        // Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int size = documentContext.read("$.length()");
        assertThat(size).isEqualTo(5);
        System.out.println(documentContext.jsonString());
        String firstName = documentContext.read("$[0].firstName");
        assertThat(firstName).isEqualTo("Michael");
    }
}