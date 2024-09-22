package cr.co.ctpcit.citsacbackend.rest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cr.co.ctpcit.citsacbackend.data.enums.WeekDays;
import cr.co.ctpcit.citsacbackend.logic.dto.dates.ExamDayDto;
import cr.co.ctpcit.citsacbackend.logic.services.ExamDayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@WebMvcTest(ExamDayController.class)
public class ExamDayControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamDayService examDayService;

    private ExamDayDto examDay1;
    private ExamDayDto examDay2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        examDay1 = new ExamDayDto(1, 1, WeekDays.M, "10:00");
        examDay2 = new ExamDayDto(2, 1, WeekDays.T, "11:00");
    }

    @Test
    void getAllExamDays() throws Exception {
        List<ExamDayDto> allExamDays = Arrays.asList(examDay1, examDay2);
        when(examDayService.getAllExamDays()).thenReturn(allExamDays);

        mockMvc.perform(get("/api/ExamDays")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].examDayId").value(examDay1.examDayId()))
                .andExpect(jsonPath("$[1].examDayId").value(examDay2.examDayId()));
    }

    @Test
    void createExamDay() throws Exception {
        when(examDayService.createExamDay(any(ExamDayDto.class))).thenReturn(examDay1);

        mockMvc.perform(post("/api/ExamDays")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"examDayId\":1,\"examPeriodId\":1,\"examDay\":\"M\",\"startTime\":\"10:00\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.examDayId").value(examDay1.examDayId()));
    }

    @Test
    void updateExamDay() throws Exception {
        when(examDayService.updateExamDay(eq(1), any(ExamDayDto.class))).thenReturn(examDay1);

        mockMvc.perform(put("/api/ExamDays/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"examDayId\":1,\"examPeriodId\":1,\"examDay\":\"M\",\"startTime\":\"10:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.examDayId").value(examDay1.examDayId()));
    }

    @Test
    void updateExamDayNotFound() throws Exception {
        when(examDayService.updateExamDay(eq(1), any(ExamDayDto.class)))
                .thenThrow(new NoSuchElementException("Día de examen no encontrado con el id 1"));

        mockMvc.perform(put("/api/ExamDays/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"examDayId\":1,\"examPeriodId\":1,\"examDay\":\"M\",\"startTime\":\"10:00\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Día de examen no encontrado con el id 1"));
    }

    @Test
    void getExamDayNotFound() throws Exception {
        when(examDayService.getAllExamDays()).thenThrow(new NoSuchElementException("Día de examen no encontrado"));

        mockMvc.perform(get("/api/ExamDays")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Día de examen no encontrado"));
    }

    @Test
    void createExamDayBadRequest() throws Exception {
        mockMvc.perform(post("/api/ExamDays")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"examDayId\":null,\"examPeriodId\":1,\"examDay\":\"Monday\",\"startTime\":\"10:00\"}"))
                .andExpect(status().isBadRequest());
    }
}
