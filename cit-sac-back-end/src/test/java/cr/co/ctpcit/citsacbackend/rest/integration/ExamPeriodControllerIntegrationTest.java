package cr.co.ctpcit.citsacbackend.rest.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.logic.dto.auth.AuthResponseDto;
import cr.co.ctpcit.citsacbackend.logic.dto.dates.ExamPeriodDto;
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

import java.text.SimpleDateFormat;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class ExamPeriodControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  AuthResponseDto authResponseDto;

  @BeforeEach
  void setUp() throws Exception {
    // @formatter:off
    MvcResult result = this.mockMvc.perform(post("/api/auth/login")
            .with(httpBasic("sysadmin@cit.co.cr", "campus12")))
        .andExpect(status().isOk())
        .andReturn();

    authResponseDto = objectMapper.readValue(result.getResponse().getContentAsString(), AuthResponseDto.class);
  }

  @Test
  public void testGetAllExamPeriods() throws Exception {
    mockMvc.perform(get("/api/exam-periods")
        .header("Authorization", "Bearer " + authResponseDto.token()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  public void testUpdateExamPeriod() throws Exception {
    int testId = 1;

    // Crear un DTO con las fechas que incluyen fecha y hora en formato completo
    ExamPeriodDto updatedPeriodDto = new ExamPeriodDto(testId,
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse("2024-09-01T00:00:00.000+00:00"),
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(
            "2024-09-30T00:00:00.000+00:00"));

    // Serializar y realizar la prueba
    mockMvc.perform(put("/api/exam-periods/{id}", testId)
            .header("Authorization", "Bearer " + authResponseDto.token())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedPeriodDto))).andExpect(status().isOk())
        .andExpect(jsonPath("$.startDate").value("2024-09-01T00:00:00.000+00:00"))
        .andExpect(jsonPath("$.endDate").value("2024-09-30T00:00:00.000+00:00"));
  }

  @Test
  public void testCreateExamPeriod() throws Exception {
    // Crear un DTO con las fechas que incluyen fecha y hora en formato completo
    ExamPeriodDto newPeriodDto = new ExamPeriodDto(3, // El ID será generado automáticamente
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse("2024-09-01T00:00:00.000+00:00"),
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(
            "2024-09-30T00:00:00.000+00:00"));

    // Realizar la prueba
    mockMvc.perform(post("/api/exam-periods")
            .header("Authorization", "Bearer " + authResponseDto.token())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(newPeriodDto))).andExpect(status().isCreated())
        .andExpect(jsonPath("$.startDate").value("2024-09-01T00:00:00.000+00:00"))
        .andExpect(jsonPath("$.endDate").value("2024-09-30T00:00:00.000+00:00"));
  }
}
