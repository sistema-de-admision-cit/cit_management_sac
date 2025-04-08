package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.TestProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class StudentDtoTest {
  @Autowired
  private JacksonTester<StudentDto> json;

  @Test
  void testSerialize() throws Exception {
    StudentDto student = TestProvider.provideStudentDto();

    assertThat(json.write(student)).isStrictlyEqualToJson("StudentDtoJsonExpected.json");
    assertThat(json.write(student)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(student)).extractingJsonPathNumberValue("@.id").isEqualTo(11);
    assertThat(json.write(student)).hasJsonPathStringValue("@.previousSchool");
    assertThat(json.write(student)).extractingJsonPathStringValue("@.previousSchool")
        .isEqualTo("Escuela La Sabana");
  }

  @Test
  void testDeserialize() throws Exception {
    String content = """
        {
          "id": 11,
          "person": {
            "id": 11,
            "firstName": "Andrés",
            "firstSurname": "Rodríguez",
            "secondSurname": "Morales",
            "idType": "CC",
            "idNumber": "200123654"
          },
          "birthDate": "2010-03-12",
          "previousSchool": "Escuela La Sabana",
          "hasAccommodations": false,
          "parents": []
        }
        """;

    StudentDto student = TestProvider.provideStudentDto();

    assertThat(json.parse(content)).isEqualTo(student);
    assertThat(json.parseObject(content).id()).isEqualTo(11);
    assertThat(json.parseObject(content).previousSchool()).isEqualTo("Escuela La Sabana");
  }
}
