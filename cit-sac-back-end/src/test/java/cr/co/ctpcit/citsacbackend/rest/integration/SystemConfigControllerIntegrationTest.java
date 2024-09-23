package cr.co.ctpcit.citsacbackend.rest.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.logic.dto.config.SystemConfigDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class SystemConfigControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void addSystemConfigIntegrationTest() throws Exception {

    SystemConfigDto systemConfigDto = new SystemConfigDto(null, "examen_academico", "40");

    mockMvc.perform(post("/api/system-config").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(systemConfigDto))).andExpect(status().isCreated())
        .andExpect(jsonPath("$.configName").value("examen_academico"))
        .andExpect(jsonPath("$.configValue").value("40"));
  }
}
