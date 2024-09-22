package cr.co.ctpcit.citsacbackend.rest;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@WebMvcTest(ExamPeriodController.class)
public class ExamPeriodControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamPeriodService examPeriodService;

    private ExamPeriodDto period1;
    private ExamPeriodDto period2;

    @BeforeEach
    void setUp() throws ParseException {
        MockitoAnnotations.openMocks(this);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate1 = sdf.parse("2024-01-01");
        Date endDate1 = sdf.parse("2024-01-15");
        Date startDate2 = sdf.parse("2024-02-01");
        Date endDate2 = sdf.parse("2024-02-15");

        period1 = new ExamPeriodDto(1, startDate1, endDate1);
        period2 = new ExamPeriodDto(2, startDate2, endDate2);
    }

    @Test
    void getAllExamPeriods() throws Exception {
        List<ExamPeriodDto> allPeriods = Arrays.asList(period1, period2);
        when(examPeriodService.getAllExamPeriods()).thenReturn(allPeriods);

        mockMvc.perform(get("/api/exam-periods")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(period1.examPeriodId()))
                .andExpect(jsonPath("$[1].id").value(period2.examPeriodId()));
    }

//    @Test
//    void getExamPeriodById() throws Exception {
//        when(examPeriodService.ExamPeriodDto(1)).thenReturn(period1);
//
//        mockMvc.perform(get("/api/exam-periods/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(period1.id()));
//    }
//
//    @Test
//    void getExamPeriodByIdNotFound() throws Exception {
//        when(examPeriodService.getExamPeriodById(1)).thenThrow(new NoSuchElementException("Periodo no encontrado con el id 1"));
//
//        mockMvc.perform(get("/api/exam-periods/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(content().string("Periodo no encontrado con el id 1"));
//    }

    @Test
    void createExamPeriod() throws Exception {
        when(examPeriodService.createExamPeriod(period1)).thenReturn(period1);

        mockMvc.perform(post("/api/exam-periods")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"startDate\":\"2024-01-01\",\"endDate\":\"2024-01-15\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.startDate").value("2024-01-01"));
    }

    @Test
    void updateExamPeriod() throws Exception {
        when(examPeriodService.updateExamPeriod(1, period1)).thenReturn(period1);

        mockMvc.perform(put("/api/exam-periods/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"startDate\":\"2024-01-01\",\"endDate\":\"2024-01-15\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startDate").value("2024-01-01"));
    }

    @Test
    void updateExamPeriodNotFound() throws Exception {
        when(examPeriodService.updateExamPeriod(1, period1)).thenThrow(new NoSuchElementException("Periodo no encontrado con el id 1"));

        mockMvc.perform(put("/api/exam-periods/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"startDate\":\"2024-01-01\",\"endDate\":\"2024-01-15\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Periodo no encontrado con el id 1"));
    }
}
