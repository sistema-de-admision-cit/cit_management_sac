package cr.co.ctpcit.citsacbackend.rest.exams;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import cr.co.ctpcit.citsacbackend.data.enums.ExamType;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.ExamDto;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.EnrollmentUpdateDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_CLASS;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"ExamsTestRollback.sql"}, executionPhase = AFTER_TEST_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ExamsControllerTest {
  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  @Sql(scripts = {"ExamsTestRollback.sql"}, executionPhase = AFTER_TEST_METHOD)
  void getAcademicExam() {
    ResponseEntity<ExamDto> response =
        restTemplate.getForEntity("/api/exams/academic-exam/{id}", ExamDto.class, 200123654);

    ExamDto examDto = response.getBody();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assert examDto != null;
    assertThat(examDto.examType()).isNotNull();
    assertThat(examDto.examType()).isEqualTo(ExamType.ACA);
    assertThat(examDto.responses()).isNotNull();
    assertThat(examDto.responses().size()).isEqualTo(20);
  }

  @Test
  void getAcademicExamNotFound() {
    ResponseEntity<String> response =
        restTemplate.getForEntity("/api/exams/academic-exam/{id}", String.class, 225566);

    DocumentContext documentContext = JsonPath.parse(response.getBody());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    //Check if the response contains the expected message
    assertThat((String) documentContext.read("$.message")).isEqualTo(
        "Verifique su número de cédula.");
  }

  @Test
  void getAcademicExamBadRequest() {
    //Generates two exam for both enrollments for the student
    ResponseEntity<String> response =
        restTemplate.getForEntity("/api/exams/academic-exam/{id}", String.class, 200123654);
    response = restTemplate.getForEntity("/api/exams/academic-exam/{id}", String.class, 200123654);

    //Generates a new exam for the student but there will not be available exams enrollments
    response = restTemplate.getForEntity("/api/exams/academic-exam/{id}", String.class, 200123654);

    DocumentContext documentContext = JsonPath.parse(response.getBody());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    //Check if the response contains the expected message
    assertThat((String) documentContext.read("$.message")).isEqualTo(
        "El estudiante no tiene inscripciones activas para examen.");
  }

  @Test
  @Sql(scripts = {"ExamsTestRollback.sql"}, executionPhase = AFTER_TEST_METHOD)
  void shouldSaveAcademicExamWithPerfectScore() throws IOException {
    //Get an exam
    ResponseEntity<ExamDto> response =
        restTemplate.getForEntity("/api/exams/academic-exam/{id}", ExamDto.class, 200123654);

    ExamDto examDto = response.getBody();
    assert examDto != null;

    ExamDto requestExamDto = objectMapper.readValue(new File(
            "src/test/resources/cr/co/ctpcit/citsacbackend/rest/exams/SaveExamJsonRequest-PerfectScore.json"),
        ExamDto.class);


    ExamDto saveExamDto = ExamDto.builder().id(examDto.id()).examType(examDto.examType())
        .enrollment(examDto.enrollment()).examDate(examDto.examDate()).examType(examDto.examType())
        .responses(requestExamDto.responses()).build();

    //Request
    HttpEntity<ExamDto> request = new HttpEntity<>(saveExamDto);
    ResponseEntity<Void> putResponse =
        restTemplate.exchange("/api/exams/save-academic-exam", HttpMethod.PUT, request, Void.class);

    assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }
}
