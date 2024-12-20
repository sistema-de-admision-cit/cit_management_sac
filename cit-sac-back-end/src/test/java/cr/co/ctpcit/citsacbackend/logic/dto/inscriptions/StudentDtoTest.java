package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.IdType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class StudentDtoTest {
  @Autowired
  private JacksonTester<StudentDto> json;

  @Test
  void testSerialize() throws Exception {
    PersonDto person = new PersonDto(1L, "John", "Doe", "Smith", IdType.CC, "123456789");
    StudentDto student =
        new StudentDto(1L, person, LocalDate.parse("2007-12-03"), "Manhattan School", false,
            new ArrayList<>());

    assertThat(json.write(student)).isStrictlyEqualToJson("StudentDtoJsonExpected.json");
    assertThat(json.write(student)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(student)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
    assertThat(json.write(student)).hasJsonPathStringValue("@.previousSchool");
    assertThat(json.write(student)).extractingJsonPathStringValue("@.previousSchool")
        .isEqualTo("Manhattan School");
  }

  @Test
  void testDeserialize() throws Exception {
    String content = """
        {
        "id": 1,
        "person": {
            "id": 1,
            "firstName": "John",
            "firstSurname": "Doe",
            "secondSurname": "Smith",
            "idType": "CC",
            "idNumber": "123456789"
        },
        "birthDate": "2007-12-03",
        "previousSchool": "Manhattan School",
        "hasAccommodations": false,
        "parents": []
        }
        """;
    PersonDto person = new PersonDto(1L, "John", "Doe", "Smith", IdType.CC, "123456789");
    StudentDto student =
        new StudentDto(1L, person, LocalDate.parse("2007-12-03"), "Manhattan School", false,
            new ArrayList<>());

    assertThat(json.parse(content)).isEqualTo(student);
    assertThat(json.parseObject(content).id()).isEqualTo(1);
    assertThat(json.parseObject(content).previousSchool()).isEqualTo("Manhattan School");
  }
}
