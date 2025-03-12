package cr.co.ctpcit.citsacbackend.logic.dto.exams;

import cr.co.ctpcit.citsacbackend.TestProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@JsonTest
class ExamDtoTest {
  @Autowired
  JacksonTester<ExamDto> tester;

  @Test
  void serializeJsonExamAca() throws IOException {
    ExamDto exam = TestProvider.provideAcaExamDto();

    assertThat(tester.write(exam)).isEqualToJson("AcademicExamDtoJsonExpected.json");
    assertThat(tester.write(exam)).hasJsonPathNumberValue("@.id");
    assertThat(tester.write(exam)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
    assertThat(tester.write(exam)).hasJsonPathStringValue("@.examType");
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
              "questionGrade": "FIRST",
              "questionLevel": "EASY",
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

    ExamDto examDto = TestProvider.provideAcaExamDto();

    assertThat(tester.parseObject(expected).id()).isEqualTo(1);
    assertThat(tester.parseObject(expected).examType()).isEqualTo(examDto.examType());
    assertThat(tester.parseObject(expected).responses().size()).isEqualTo(
        examDto.responses().size());
  }

  @Test
  void serializeJsonExamDai() throws IOException {
    ExamDto exam = TestProvider.provideDaiExamDto();

    assertThat(tester.write(exam)).isEqualToJson("DaiExamDtoJsonExpected.json");
    assertThat(tester.write(exam)).hasJsonPathNumberValue("@.id");
    assertThat(tester.write(exam)).extractingJsonPathNumberValue("@.id").isEqualTo(2);
    assertThat(tester.write(exam)).hasJsonPathStringValue("@.examType");
  }

  @Test
  void deserializeJsonExamDai() throws IOException {
    String expected = """
        {
          "id": 2,
          "enrollment": 1,
          "examDate": "1970-01-01T00:00:00Z",
          "examType": "DAI",
          "responses": [
            {
              "id": 1,
              "questionType": "DAI",
              "questionText": "¿Cómo te sientes el día de hoy?",
              "imageUrl": null,
              "questionGrade": "SECOND",
              "questionLevel": "EASY",
              "selectionType": "PARAGRAPH",
              "deleted": false,
              "response": "Me siento muy bien, aunque con un poco de sueño."
            }
          ]
        }
        """;

    ExamDto examDto = TestProvider.provideDaiExamDto();

    assertThat(tester.parseObject(expected).id()).isEqualTo(2);
    assertThat(tester.parseObject(expected).examType()).isEqualTo(examDto.examType());
    assertThat(tester.parseObject(expected).responses().size()).isEqualTo(
        examDto.responses().size());
  }
}
