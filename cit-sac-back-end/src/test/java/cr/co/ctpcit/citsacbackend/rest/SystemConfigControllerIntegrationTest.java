package cr.co.ctpcit.citsacbackend.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.logic.dto.SystemConfigDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        // Arrange
        SystemConfigDto systemConfigDto = new SystemConfigDto(null, "max_connections", "200");

        // Act & Assert
        mockMvc.perform(post("/api/system-config")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(systemConfigDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.configName").value("max_connections"))
                .andExpect(jsonPath("$.configValue").value("200"));
    }
}
