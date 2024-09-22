package cr.co.ctpcit.citsacbackend.rest;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cr.co.ctpcit.citsacbackend.data.enums.WeekDays;
import cr.co.ctpcit.citsacbackend.logic.dto.dates.ExamDayDto;
import cr.co.ctpcit.citsacbackend.logic.services.ExamDayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    private ExamDayDto day1;
    private ExamDayDto day2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        day1 = new ExamDayDto(1, 1, WeekDays.M, "10:00");
        day2 = new ExamDayDto(2, 1, WeekDays.K, "11:00");
    }

    @Test
    void getAllExamDays() throws Exception {
        List<ExamDayDto> allDays = Arrays.asList(day1, day2);
        when(examDayService.getAllExamDays()).thenReturn(allDays);

        mockMvc.perform(get("/api/exam-days")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(day1.examDayId()))
                .andExpect(jsonPath("$[1].id").value(day2.examDayId()));
    }

//    @Test
//    void getExamDayById() throws Exception {
//        when(examDayService.getExamDayById(1)).thenReturn(day1);
//
//        mockMvc.perform(get("/api/exam-days/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(day1.id()));
//    }
//
//    @Test
//    void getExamDayByIdNotFound() throws Exception {
//        when(examDayService.getExamDayById(1)).thenThrow(new NoSuchElementException("Día no encontrado con el id 1"));
//
//        mockMvc.perform(get("/api/exam-days/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("Día no encontrado con el id 1"));
//    }

    @Test
    void createExamDay() throws Exception {
        when(examDayService.createExamDay(day1)).thenReturn(day1);

        mockMvc.perform(post("/api/exam-days")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"examPeriodId\":1,\"examDay\":\"M\",\"startTime\":\"10:00\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.examDay").value("M"));
    }

    @Test
    void updateExamDay() throws Exception {
        when(examDayService.updateExamDay(1, day1)).thenReturn(day1);

        mockMvc.perform(put("/api/exam-days/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"examPeriodId\":1,\"examDay\":\"M\",\"startTime\":\"10:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.examDay").value("M"));
    }

    @Test
    void updateExamDayNotFound() throws Exception {
        when(examDayService.updateExamDay(1, day1)).thenThrow(new NoSuchElementException("Día no encontrado con el id 1"));

        mockMvc.perform(put("/api/exam-days/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"examPeriodId\":1,\"examDay\":\"M\",\"startTime\":\"10:00\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Día no encontrado con el id 1"));
    }
}
