package cr.co.ctpcit.citsacbackend.rest.questions;

import com.fasterxml.jackson.databind.ObjectMapper;
import cr.co.ctpcit.citsacbackend.TestConfig;
import cr.co.ctpcit.citsacbackend.TestProvider;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionDto;
import cr.co.ctpcit.citsacbackend.logic.dto.questions.QuestionFilterSpec;
import cr.co.ctpcit.citsacbackend.logic.services.auth.UserDetailsServiceImpl;
import cr.co.ctpcit.citsacbackend.logic.services.questions.QuestionsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(QuestionsController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestConfig.class)
class QuestionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private QuestionsServiceImpl questionService;

    @Autowired
    private TestConfig testConfig;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCreateQuestion() throws Exception {
        QuestionDto questionDto = TestProvider.provideQuestionDto();
        when(questionService.createQuestion(any(QuestionDto.class), any())).thenReturn(questionDto);

        MockMultipartFile jsonFile = new MockMultipartFile("question", "", "application/json", objectMapper.writeValueAsBytes(questionDto));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/questions/create")
                        .file(jsonFile)
                        .headers(testConfig.getBearerHeader())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.questionText").value(questionDto.questionText()));
    }


    @Test
    void testGetQuestionById() throws Exception {
        QuestionDto questionDto = TestProvider.provideQuestionDto();

        when(questionService.getQuestionById(2L)).thenReturn(questionDto);

        mockMvc.perform(get("/api/questions/get-by-id/2")
                        .headers(testConfig.getBearerHeader()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.questionText").value(questionDto.questionText()));
    }

    @Test
    void testUpdateQuestion() throws Exception {
        QuestionDto questionDto = TestProvider.provideQuestionDto();
        when(questionService.updateQuestion(any(QuestionDto.class), any())).thenReturn(questionDto);

        MockMultipartFile jsonFile = new MockMultipartFile("question", "", "application/json", objectMapper.writeValueAsBytes(questionDto));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/questions/update")
                        .file(jsonFile)
                        .headers(testConfig.getBearerHeader())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(request -> {
                            request.setMethod("POST");
                            return request;
                        }))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.questionText").value(questionDto.questionText()));
    }

    @Test
    void testDeleteQuestion() throws Exception {
        doNothing().when(questionService).deleteQuestion(2L);

        mockMvc.perform(delete("/api/questions/delete/2")
                        .headers(testConfig.getBearerHeader()))
                .andExpect(status().isOk())
                .andExpect(content().string("Question deleted successfully."));
    }
    @Test
    void testGetAllQuestions() throws Exception {
        QuestionDto questionDto = TestProvider.provideDaiQuestionDto();
        Page<QuestionDto> questionPage = new PageImpl<>(Collections.singletonList(questionDto), PageRequest.of(0, 10), 1);

        when(questionService.getQuestions(any(QuestionFilterSpec.class), any(Pageable.class))).thenReturn(questionPage);

        mockMvc.perform(get("/api/questions/get-all")
                        .headers(testConfig.getBearerHeader())
                        .param("page", "0")
                        .param("size", "10")
                        .param("questionText", "")
                        .param("deleted", "false")
                        .param("questionType", "")
                        .param("grade", "")
                        .param("questionLevel", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].questionText").value("¿Cómo te sientes el día de hoy?"))
                .andExpect(jsonPath("$.content[0].questionType").value("DAI"))
                .andExpect(jsonPath("$.content[0].questionGrade").value("SECOND"))
                .andExpect(jsonPath("$.content[0].questionLevel").value("EASY"))
                .andExpect(jsonPath("$.content[0].selectionType").value("PARAGRAPH"))
                .andExpect(jsonPath("$.content[0].deleted").value(false));
    }

    @Test
    void testSearchQuestion() throws Exception {
        QuestionDto questionDto = TestProvider.provideDaiQuestionDto();
        Page<QuestionDto> questionPage = new PageImpl<>(Collections.singletonList(questionDto), PageRequest.of(0, 10), 1);

        when(questionService.searchQuestion(any(String.class), any(Pageable.class))).thenReturn(questionPage);

        mockMvc.perform(get("/api/questions/search")
                        .headers(testConfig.getBearerHeader())
                        .param("questionText", "¿Cómo te sientes el día de hoy?")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].questionText").value("¿Cómo te sientes el día de hoy?"))
                .andExpect(jsonPath("$.content[0].questionType").value("DAI"))
                .andExpect(jsonPath("$.content[0].questionGrade").value("SECOND"))
                .andExpect(jsonPath("$.content[0].questionLevel").value("EASY"))
                .andExpect(jsonPath("$.content[0].selectionType").value("PARAGRAPH"))
                .andExpect(jsonPath("$.content[0].deleted").value(false));
    }




    @Test
    void testHealthCheck() throws Exception {
        mockMvc.perform(get("/api/questions/health")
                        .headers(testConfig.getBearerHeader()))
                .andExpect(status().isOk())
                .andExpect(content().string("Questions service is up and running."));
    }
}
