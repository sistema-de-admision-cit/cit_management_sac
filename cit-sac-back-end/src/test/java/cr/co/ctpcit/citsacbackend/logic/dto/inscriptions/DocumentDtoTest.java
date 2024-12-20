package cr.co.ctpcit.citsacbackend.logic.dto.inscriptions;

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
    DocumentDto document = new DocumentDto(new DocumentIdDto(1L, 1L), "Document 1", DocType.OT);

    assertThat(json.write(document)).isStrictlyEqualToJson("DocumentDtoJsonExpected.json");
    assertThat(json.write(document)).hasJsonPathNumberValue("@.id.enrollmentId");
    assertThat(json.write(document)).extractingJsonPathNumberValue("@.id.enrollmentId")
        .isEqualTo(1);
  }

  @Test
  void testDeserialize() throws Exception {
    String content = """
        {
          "id": {
            "documentId": 1,
            "enrollmentId": 1
          },
          "documentName": "Document 1",
          "documentType": "OT"
        }
        """;
    DocumentDto document = new DocumentDto(new DocumentIdDto(1L, 1L), "Document 1", DocType.OT);

    assertThat(json.parse(content)).isEqualTo(document);
    assertThat(json.parseObject(content).id().enrollmentId()).isEqualTo(1);
  }

}
