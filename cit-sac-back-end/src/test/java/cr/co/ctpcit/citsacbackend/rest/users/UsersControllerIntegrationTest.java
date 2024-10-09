package cr.co.ctpcit.citsacbackend.rest.users;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@Rollback
class UsersControllerIntegrationTest {

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

  @Rollback
  @Test
  void createUser() throws Exception {
    this.mvc.perform(
            post("/api/users/create-user").header("Authorization", "Bearer " + authResponseDto.token())
                .contentType("application/json")
                .content("{\"username\":\"manuel@ctpcit.co.cr\",\"role\":\"A\"}"))
        .andExpect(status().isCreated());
  }

  @Test
    void getUsers() throws Exception {
    this.mvc.perform(get("/api/users").header("Authorization", "Bearer " + authResponseDto.token()))
        .andExpect(status().isOk());
    }

  @Test
  void getUsersWithCustomPagination() throws Exception {
    this.mvc.perform(get("/api/users").param("page", "0").param("size", "3")
        .header("Authorization", "Bearer " + authResponseDto.token())).andExpect(status().isOk());
  }

  @Test
  void getUser() throws Exception {
    this.mvc.perform(get("/api/users/user/1").header("Authorization", "Bearer " + authResponseDto.token()))
        .andExpect(status().isOk());
  }

  @Test
  void getUserByEmail()throws Exception {
    this.mvc.perform(get("/api/users/user").param("email", "jorge@cit.co.cr")
        .header("Authorization", "Bearer " + authResponseDto.token())).andExpect(status().isOk());
  }

  @Rollback
  @Test
  void deleteUser() throws Exception {
    this.mvc.perform(delete("/api/users/delete-user").param("email", "jorge@cit.co.cr")
        .header("Authorization", "Bearer " + authResponseDto.token())).andExpect(status().isNoContent());
  }
}
