package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

import cr.co.ctpcit.citsacbackend.TestProvider;
import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JsonTest
class DocumentDtoTest {
  @Autowired
  private JacksonTester<DocumentDto> json;

  @Test
  void testSerialize() throws Exception {
    long timestamp = 856332114336L;
    String documentUrlPostfix =
        "grades_" + TestProvider.provideStudentDto().person().idNumber() + "_" + timestamp + ".pdf";

    DocumentDto document =
        new DocumentDto(1L, documentUrlPostfix, DocType.OT, "Documento de notas");

    assertThat(json.write(document)).isStrictlyEqualToJson("DocumentDtoJsonExpected.json");
    assertThat(json.write(document)).hasJsonPathNumberValue("@.id");
    assertThat(json.write(document)).extractingJsonPathNumberValue("@.id").isEqualTo(1);
  }

  @Test
  void testDeserialize() throws Exception {
    String content = """
        {
          "id": 1,
          "documentUrlPostfix": "grades_200123654_856332114336.pdf",
          "documentType": "OT",
          "documentName": "Documento de notas"
        }
        """;
    long timestamp = 856332114336L;
    String documentUrlPostfix =
        "grades_" + TestProvider.provideStudentDto().person().idNumber() + "_" + timestamp + ".pdf";

    DocumentDto document =
        new DocumentDto(1L, documentUrlPostfix, DocType.OT, "Documento de notas");

    assertThat(json.parse(content)).isEqualTo(document);
    assertThat(json.parseObject(content).id()).isEqualTo(1);
  }

}
