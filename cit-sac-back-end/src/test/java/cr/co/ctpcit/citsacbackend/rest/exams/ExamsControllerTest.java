package cr.co.ctpcit.citsacbackend.rest.exams;

import cr.co.ctpcit.citsacbackend.data.enums.ExamType;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.ExamDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_CLASS;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"ExamsTestRollback.sql"}, executionPhase = AFTER_TEST_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExamsControllerTest {
  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @Sql(scripts = {"ExamsTestRollback.sql"}, executionPhase = AFTER_TEST_METHOD)
  void getAcademicExam() {
    ResponseEntity<ExamDto> response =
        restTemplate.getForEntity("/api/exams/academic-exam/{id}", ExamDto.class, 200123654);

    ExamDto examDto = response.getBody();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(examDto).isNotNull();
    assertThat(examDto.examType()).isNotNull();
    assertThat(examDto.examType()).isEqualTo(ExamType.ACA);
    assertThat(examDto.responses()).isNotNull();
    assertThat(examDto.responses().size()).isEqualTo(20);

  }
}
