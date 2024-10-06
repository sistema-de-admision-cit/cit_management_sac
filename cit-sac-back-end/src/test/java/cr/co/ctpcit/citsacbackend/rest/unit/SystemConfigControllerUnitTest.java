package cr.co.ctpcit.citsacbackend.rest.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.data.entities.config.SystemConfigEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.AuthResponseDto;
import cr.co.ctpcit.citsacbackend.logic.services.SystemConfigServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@Rollback
public class SystemConfigControllerUnitTest {

  AuthResponseDto authResponseDto;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private SystemConfigServiceImplementation systemConfigService;
  private List<SystemConfigEntity> examPercentages;
  private List<SystemConfigEntity> notifications;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);


    // Mock notifications list
    notifications =
        Arrays.asList(new SystemConfigEntity(1, "email_contact", "complejoEducativo@ctpcit.com"),
            new SystemConfigEntity(2, "email_notifications_contact",
                "notificacionesCIT@ctpcit.com"),
            new SystemConfigEntity(3, "whatsapp_contact", "88950252"),
            new SystemConfigEntity(4, "office_contact", "22370186"),
            new SystemConfigEntity(5, "instagram_contact", "ComplejoEducativoCIT"),
            new SystemConfigEntity(6, "facebook_contact", "ComplejoEducativoCIT"));

    MvcResult result = this.mockMvc.perform(
            post("/api/auth/login").with(httpBasic("sysadmin@cit.co.cr", "campus12")))
        .andExpect(status().isOk()).andReturn();

    authResponseDto =
        objectMapper.readValue(result.getResponse().getContentAsString(), AuthResponseDto.class);
  }


  @Test
  void getNotifications() throws Exception {
    when(systemConfigService.getNotifications("contact")).thenReturn(notifications);

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
  void updateNotifications() throws Exception {
    List<SystemConfigEntity> updatedNotifications =
        Arrays.asList(new SystemConfigEntity(1, "email_contact", "complejoEducativo@ctpcit.com"),
            new SystemConfigEntity(2, "email_notifications_contact",
                "notificacionesCIT@ctpcit.com"),
            new SystemConfigEntity(3, "whatsapp_contact", "88950252"),
            new SystemConfigEntity(4, "office_contact", "22370186"),
            new SystemConfigEntity(5, "instagram_contact", "ComplejoEducativoCIT"),
            new SystemConfigEntity(6, "facebook_contact", "ComplejoEducativoCIT"));

    doNothing().when(systemConfigService)
        .updateNotifications("complejoEducativo@ctpcit.com", "notificacionesCIT@ctpcit.com",
            "88950252", "22370186", "ComplejoEducativoCIT", "ComplejoEducativoCIT");
    when(systemConfigService.getNotifications("contact")).thenReturn(updatedNotifications);

    mockMvc.perform(put("/api/system-config/update-notifications").param("email_contact",
                "complejoEducativo@ctpcit.com")
            .param("email_notifications_contact", "notificacionesCIT@ctpcit.com")
            .param("whatsapp_contact", "88950252").param("office_contact", "22370186")
            .param("instagram_contact", "ComplejoEducativoCIT")
            .param("facebook_contact", "ComplejoEducativoCIT")
            .header("Authorization", "Bearer " + authResponseDto.token())
            .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(jsonPath("$[0].configName").value("email_contact"))
        .andExpect(jsonPath("$[0].configValue").value("complejoEducativo@ctpcit.com"))
        .andExpect(jsonPath("$[1].configName").value("email_notifications_contact"))
        .andExpect(jsonPath("$[1].configValue").value("notificacionesCIT@ctpcit.com"))
        .andExpect(jsonPath("$[2].configName").value("whatsapp_contact"))
        .andExpect(jsonPath("$[2].configValue").value("88950252"))
        .andExpect(jsonPath("$[3].configName").value("office_contact"))
        .andExpect(jsonPath("$[3].configValue").value("22370186"))
        .andExpect(jsonPath("$[4].configName").value("instagram_contact"))
        .andExpect(jsonPath("$[4].configValue").value("ComplejoEducativoCIT"))
        .andExpect(jsonPath("$[5].configName").value("facebook_contact"))
        .andExpect(jsonPath("$[5].configValue").value("ComplejoEducativoCIT"));
  }

}



