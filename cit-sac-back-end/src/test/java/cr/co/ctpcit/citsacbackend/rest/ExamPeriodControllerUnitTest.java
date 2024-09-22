package cr.co.ctpcit.citsacbackend.rest;

import cr.co.ctpcit.citsacbackend.logic.dto.dates.ExamPeriodDto;
import cr.co.ctpcit.citsacbackend.logic.services.ExamPeriodService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExamPeriodController.class)
public class ExamPeriodControllerUnitTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ExamPeriodService examPeriodService;

  private ExamPeriodDto examPeriod1;
  private ExamPeriodDto examPeriod2;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    examPeriod1 = new ExamPeriodDto(1, new Date(), new Date());
    examPeriod2 = new ExamPeriodDto(2, new Date(), new Date());
  }

  @Test
  void getAllExamPeriods() throws Exception {
    List<ExamPeriodDto> allExamPeriods = Arrays.asList(examPeriod1, examPeriod2);
    when(examPeriodService.getAllExamPeriods()).thenReturn(allExamPeriods);

    mockMvc.perform(get("/api/ExamPeriods").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].examPeriodId").value(examPeriod1.examPeriodId()))
        .andExpect(jsonPath("$[1].examPeriodId").value(examPeriod2.examPeriodId()));
  }

  @Test
  void createExamPeriod() throws Exception {
    when(examPeriodService.createExamPeriod(any(ExamPeriodDto.class))).thenReturn(examPeriod1);

    mockMvc.perform(post("/api/ExamPeriods").contentType(MediaType.APPLICATION_JSON).content(
            "{\"examPeriodId\":1,\"startDate\":\"2024-09-01T00:00:00.000+00:00\",\"endDate\":\"2024-09-10T00:00:00.000+00:00\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.examPeriodId").value(examPeriod1.examPeriodId()));
  }

  @Test
  void updateExamPeriod() throws Exception {
    when(examPeriodService.updateExamPeriod(eq(1), any(ExamPeriodDto.class))).thenReturn(
        examPeriod1);

    mockMvc.perform(put("/api/ExamPeriods/1").contentType(MediaType.APPLICATION_JSON).content(
            "{\"examPeriodId\":1,\"startDate\":\"2024-09-01T00:00:00.000+00:00\",\"endDate\":\"2024-09-10T00:00:00.000+00:00\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.examPeriodId").value(examPeriod1.examPeriodId()));
  }

  @Test
  void updateExamPeriodNotFound() throws Exception {
    when(examPeriodService.updateExamPeriod(eq(1), any(ExamPeriodDto.class))).thenThrow(
        new NoSuchElementException("Periodo de examen no encontrado con el id 1"));

    mockMvc.perform(put("/api/ExamPeriods/1").contentType(MediaType.APPLICATION_JSON).content(
            "{\"examPeriodId\":1,\"startDate\":\"2024-09-01T00:00:00.000+00:00\",\"endDate\":\"2024-09-10T00:00:00.000+00:00\"}"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("Periodo de examen no encontrado con el id 1"));
  }

  @Test
  void createExamPeriodBadRequest() throws Exception {
    mockMvc.perform(post("/api/ExamPeriods").contentType(MediaType.APPLICATION_JSON).content(
            "{\"examPeriodId\":null,\"startDate\":\"InvalidDate\",\"endDate\":\"2024-09-10T00:00:00.000+00:00\"}"))
        .andExpect(status().isBadRequest());
  }
}
