package cr.co.ctpcit.citsacbackend.rest.inscriptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.AuthResponseDto;
import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InscriptionFormControllerTest {
  @Autowired
  private MockMvc mvc;

  @MockBean
  private StorageService storageService;

  @Test
  @Order(1)
  public void testCreateInscription_shouldCreateANewInscription() throws Exception {
    // Arrange
    // Create sample enrollment
    String inscription = Files.readString(Paths.get("src/test/resources/inscriptionExpected.json"));

    //Crear multipartfile simulado para grades
    MockMultipartFile grades =
        new MockMultipartFile("grades", "grades.pdf", MediaType.APPLICATION_PDF_VALUE, "Some data".getBytes(StandardCharsets.UTF_8));

    MockMultipartFile inscriptionFile =
        new MockMultipartFile("inscription", "inscription", MediaType.APPLICATION_JSON_VALUE, inscription.getBytes(StandardCharsets.UTF_8));
    // Act & Assert
    mvc.perform(multipart("/api/inscription/add")
            .file(grades)
            .file(inscriptionFile))
        .andExpect(status().isOk());
  }
}
