package cr.co.ctpcit.citsacbackend.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.data.enums.WeekDays;
import cr.co.ctpcit.citsacbackend.logic.dto.dates.ExamDayDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class ExamDayControllerIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testGetAllExamDays() throws Exception {
    mockMvc.perform(get("/api/ExamDays")).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  public void testUpdateExamDay() throws Exception {
    int testId = 1;
    ExamDayDto examDayDto = new ExamDayDto(testId, 1, WeekDays.M, "08:00");

    mockMvc.perform(put("/api/ExamDays/{id}", testId).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(examDayDto))).andExpect(status().isOk())
        .andExpect(jsonPath("$.startTime").value("08:00"));
  }

  @Test
  public void testCreateExamDay() throws Exception {
    ExamDayDto examDayDto = new ExamDayDto(3, 1, WeekDays.M, "09:00");

    mockMvc.perform(post("/api/ExamDays").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(examDayDto))).andExpect(status().isCreated())
        .andExpect(jsonPath("$.examDay").value("M"))
        .andExpect(jsonPath("$.startTime").value("09:00"));
  }
}
