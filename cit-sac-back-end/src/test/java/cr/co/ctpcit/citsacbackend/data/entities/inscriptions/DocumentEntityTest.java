package cr.co.ctpcit.citsacbackend.data.entities.inscriptions;

import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class DocumentEntityTest {
  @Autowired
  private JacksonTester<DocumentEntity> json;

  @Test
  void serializeJson() throws IOException {
    DocumentEntity document = new DocumentEntity();
    document.setId(1L);
    document.setDocumentName("Document 1");
    document.setDocType(DocType.OT);
    document.setDocumentUrl("path/to/document");

    assertThat(json.write(document)).isStrictlyEqualToJson("DocumentEntityJsonExpected.json");
    assertThat(json.write(document)).hasJsonPathValue("@.id");
    assertThat(json.write(document)).extractingJsonPathValue("@.id").isEqualTo(1);
  }

  @Test
  void deserializeJson() throws IOException {
    String expected = """
        {
          "id": 1,
          "documentName": "Document 1",
          "documentType": "OT",
          "documentUrl": "path/to/document"
        }
        """;
    DocumentEntity document = new DocumentEntity();
    document.setId(1L);
    document.setDocumentName("Document 1");
    document.setDocType(DocType.OT);
    document.setDocumentUrl("path/to/document");

    assertThat(json.parseObject(expected).getId()).isEqualTo(1);
    assertThat(json.parseObject(expected).getDocumentUrl()).isEqualTo("path/to/document");
  }
}
