package cr.co.ctpcit.citsacbackend.rest.exams;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import cr.co.ctpcit.citsacbackend.data.enums.ExamType;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ManagementExamControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @Order(1)
  void getAcademicExamsOfAStudent() {
    ResponseEntity<String> response =
        restTemplate.getForEntity("/api/management-exams/academic-exams/270456789", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());

    int enrollmentCount = documentContext.read("$.length()");
    assertThat(enrollmentCount).isEqualTo(1);
  }

  @Test
  @Order(2)
  void getAcademicExamsOfANonExistentStudent() {
    ResponseEntity<String> response =
            restTemplate.getForEntity("/api/management-exams/academic-exams/999999999", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  @Order(3)
  void getAcademicExamsOfAStudentWithNoExams() {
    ResponseEntity<String> response =
            restTemplate.getForEntity("/api/management-exams/academic-exams/230987654", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    DocumentContext documentContext = JsonPath.parse(response.getBody());

    int enrollmentCount = documentContext.read("$.length()");
    assertThat(enrollmentCount).isEqualTo(0);
  }

}
