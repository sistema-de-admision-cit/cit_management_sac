package cr.co.ctpcit.citsacbackend.logic.dto.exams;

import cr.co.ctpcit.citsacbackend.TestProvider;
import cr.co.ctpcit.citsacbackend.logic.dto.exams.academic.ExamAcaDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class ExamAcaDtoTest {
  @Autowired
  JacksonTester<ExamAcaDto> tester;

  @Test
  void serializeJsonExamAca() throws IOException {
    ExamAcaDto exam = TestProvider.provideAcaExamDto();
    String json = tester.write(exam).getJson();
    assertThat(tester.write(exam))
            .hasJsonPathStringValue("@.enrollment")
            .extractingJsonPathStringValue("@.enrollment")
            .isEqualTo("1");
  }

  @Test
  void deserializeJsonAcaExam() throws IOException {
    String expected = """
        {
          "id": 1,
          "enrollment": 1,
          "examDate": "1970-01-01T00:00:00Z",
          "examType": "ACA",
          "responses": [
            {
              "id": 2,
              "questionType": "ACA",
              "questionText": "¿Como se calcula el area de un circulo?",
              "imageUrl": null,
              "selectionType": "SINGLE",
              "deleted": false,
              "questionOptions": [
                {
                  "id": 1,
                  "isCorrect": true,
                  "option": "π * radio^2",
                  "selected": true
                },
                {
                  "id": 2,
                  "isCorrect": false,
                  "option": "2 * radio",
                  "selected": false
                },
                {
                  "id": 3,
                  "isCorrect": false,
                  "option": "π * diámetro",
                  "selected": false
                },
                {
                  "id": 4,
                  "isCorrect": false,
                  "option": "radio * altura",
                  "selected": false
                }
              ]
            }
          ]
        }
        """;

    ExamAcaDto examDto = TestProvider.provideAcaExamDto();

    assertThat(tester.parseObject(expected).id()).isEqualTo(1);
    assertThat(tester.parseObject(expected).examType()).isEqualTo(examDto.examType());
    assertThat(tester.parseObject(expected).responses().size()).isEqualTo(
        examDto.responses().size());
  }
}
