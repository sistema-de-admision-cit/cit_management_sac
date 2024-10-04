package cr.co.ctpcit.citsacbackend.rest.integration.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.AuthResponseDto;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.ChangePasswordRequestDTO;
import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageService;
import cr.co.ctpcit.citsacbackend.security.DaoAuthenticationProviderCstm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@Rollback
class AuthControllerTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Autowired
  MockMvc mvc;

  @Autowired
  ObjectMapper objectMapper;

  @MockBean
  StorageService storageService;

  AuthResponseDto authResponseDto;

  @BeforeEach
  void setUp() throws Exception {
    // @formatter:off
    MvcResult result = this.mvc.perform(post("/api/auth/login")
            .with(httpBasic("sysadmin@cit.co.cr", "campus12")))
        .andExpect(status().isOk())
        .andReturn();

    authResponseDto = objectMapper.readValue(result.getResponse().getContentAsString(), AuthResponseDto.class);
  }

  @Test
  void returnDataFromEndpointWhenUserIsAuthenticated() throws Exception {
    this.mvc.perform(get("/api/questions-academic")
            .header("Authorization", "Bearer " + authResponseDto.token()))
        .andExpect(status().isOk())
        .andExpect(content().json("[\n" +
            "    {\n" +
            "        \"id\": 1,\n" +
            "        \"questionText\": \"¿Cual es la capital de Francia?\",\n" +
            "        \"questionGrade\": \"SECOND\",\n" +
            "        \"option_A\": \"Paris\",\n" +
            "        \"option_B\": \"Madrid\",\n" +
            "        \"option_C\": \"Londres\",\n" +
            "        \"option_D\": \"San Jose\",\n" +
            "        \"correctOption\": \"A\",\n" +
            "        \"imageUrl\": null\n" +
            "    }\n" +
            "]"));
    // @formatter:on
  }

  @Test
  void returnUnauthorizedExceptionWhenUserIsNotAuthenticated() throws Exception {
    this.mvc.perform(get("/api/questions-academic")).andExpect(status().isUnauthorized());
  }

  /*@Test
  void returnOkWhenUserChangePasswordCorrectly() throws Exception {
    ChangePasswordRequestDTO request =
        new ChangePasswordRequestDTO("campus12", "campus123", "campus123");
    doNothing().when(daoAuthenticationProvider).updatePassword(request);


    // @formatter:off
    this.mvc.perform(post("/api/auth/change-password")
            .header("Authorization", "Bearer " + authResponseDto.token())
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().string("Contraseña actualizada correctamente."));
    // @formatter:on
  }*/
}
