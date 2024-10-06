package cr.co.ctpcit.citsacbackend.rest.unit.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.AuthResponseDto;
import cr.co.ctpcit.citsacbackend.logic.services.storage.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@Rollback
class AuthControllerTest {
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
  @Rollback
  void changePassword_shouldReturnOkIfCorrectRequestIsMade()throws Exception {
    // Test the changePassword method
    this.mvc.perform(put("/api/auth/change-password")
            .header("Authorization", "Bearer " + authResponseDto.token())
        .contentType("application/json")
        .content("{\"currentPassword\":\"campus12\",\"newPassword\":\"campus123\", \"confirmPassword\":\"campus123\"}"))
        .andExpect(status().isOk())
        .andExpect(mvcResult -> mvcResult.getResponse().getContentAsString().equals("Contraseña actualizada correctamente."));
  }

  @Test
  void changePassword_shouldReturnBadRequestIfCurrentPasswordIsIncorrect()throws Exception {
    // Test the changePassword method
    this.mvc.perform(put("/api/auth/change-password")
            .header("Authorization", "Bearer " + authResponseDto.token())
        .contentType("application/json")
        .content("{\"currentPassword\":\"campus123\",\"newPassword\":\"campus1234\", \"confirmPassword\":\"campus1234\"}"))
        .andExpect(status().isUnauthorized())
        .andExpect(mvcResult -> mvcResult.getResponse().getContentAsString().equals("Contraseña actual incorrecta."));
  }

  @Test
  void changePassword_shouldReturnBadRequestIfNewPasswordAndConfirmPasswordAreDifferent()throws Exception {
    // Test the changePassword method
    this.mvc.perform(put("/api/auth/change-password")
            .header("Authorization", "Bearer " + authResponseDto.token())
        .contentType("application/json")
        .content("{\"currentPassword\":\"campus12\",\"newPassword\":\"campus123\", \"confirmPassword\":\"campus1234\"}"))
        .andExpect(status().isBadRequest())
        .andExpect(mvcResult -> mvcResult.getResponse().getContentAsString().equals("Las contraseñas no coinciden."));
  }

  @Test
  void changePassword_shouldReturnBadRequestIfNewPasswordIsEqualToCurrentPassword()throws Exception {
      // Test the changePassword method
      this.mvc.perform(put("/api/auth/change-password")
              .header("Authorization", "Bearer " + authResponseDto.token())
          .contentType("application/json")
          .content("{\"currentPassword\":\"campus12\",\"newPassword\":\"campus12\", \"confirmPassword\":\"campus12\"}"))
          .andExpect(status().isBadRequest())
          .andExpect(mvcResult -> mvcResult.getResponse().getContentAsString().equals("La nueva contraseña no puede ser igual a la actual."));
  }
}
