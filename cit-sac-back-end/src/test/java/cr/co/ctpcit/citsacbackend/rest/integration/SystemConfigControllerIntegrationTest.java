package cr.co.ctpcit.citsacbackend.rest.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.data.entities.config.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.AuthResponseDto;
import cr.co.ctpcit.citsacbackend.logic.services.SystemConfigServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class SystemConfigControllerIntegrationTest {

  AuthResponseDto authResponseDto;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private SystemConfigServiceImplementation systemConfigService;
  private List<SystemConfigEntity> notifications;

  @BeforeEach
  void setUp() throws Exception {

    // Mock notifications list
    notifications =
        Arrays.asList(new SystemConfigEntity(1, "email_contact", "complejoEducativo@ctpcit.com"),
            new SystemConfigEntity(2, "email_notifications_contact",
                "notificacionesCIT@ctpcit.com"),
            new SystemConfigEntity(3, "whatsapp_contact", "88950252"),
            new SystemConfigEntity(4, "office_contact", "22370186"),
            new SystemConfigEntity(5, "instagram_contact", "ComplejoEducativoCIT"),
            new SystemConfigEntity(6, "facebook_contact", "ComplejoEducativoCIT"));

    // Login to obtain auth token
    MvcResult result = this.mockMvc.perform(
            post("/api/auth/login").with(httpBasic("sysadmin@cit.co.cr", "campus12")))
        .andExpect(status().isOk()).andReturn();

    authResponseDto =
        objectMapper.readValue(result.getResponse().getContentAsString(), AuthResponseDto.class);
  }


  @Test
  void getNotificationsIntegration() throws Exception {

    mockMvc.perform(get("/api/system-config/get-notifications").header("Authorization",
            "Bearer " + authResponseDto.token()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$[0].configName").value("email_contact"))
        .andExpect(jsonPath("$[1].configName").value("email_notifications_contact"))
        .andExpect(jsonPath("$[2].configName").value("whatsapp_contact"))
        .andExpect(jsonPath("$[3].configName").value("office_contact"))
        .andExpect(jsonPath("$[4].configName").value("instagram_contact"))
        .andExpect(jsonPath("$[5].configName").value("facebook_contact"));
  }

  @Test
  void updateNotificationsIntegration() throws Exception {


    mockMvc.perform(put("/api/system-config/update-notifications").param("email_contact",
            "nuevoEmail@ejemplo.com")
        .param("email_notifications_contact", "nuevoNotificaciones@ejemplo.com")
        .param("whatsapp_contact", "61592479").param("office_contact", "22370175")
        .param("instagram_contact", "Complejo Educativo CIT")
        .param("facebook_contact", "Complejo Educativo CIT")
        .header("Authorization", "Bearer " + authResponseDto.token())
        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    // Verify the updated values
    mockMvc.perform(get("/api/system-config/get-notifications").header("Authorization",
            "Bearer " + authResponseDto.token()).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].configValue").value("nuevoEmail@ejemplo.com"))
        .andExpect(jsonPath("$[1].configValue").value("nuevoNotificaciones@ejemplo.com"))
        .andExpect(jsonPath("$[2].configValue").value("61592479"))
        .andExpect(jsonPath("$[3].configValue").value("22370175"))
        .andExpect(jsonPath("$[4].configValue").value("Complejo Educativo CIT"))
        .andExpect(jsonPath("$[5].configValue").value("Complejo Educativo CIT"));
  }
}
