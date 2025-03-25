package cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions;

import cr.co.ctpcit.citsacbackend.TestProvider;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.DocumentEntity;
import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.EnrollmentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.DocumentDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentMapperTest {

  @Test
  void testConvertToDto() {
    EnrollmentEntity enrollmentEntity = new EnrollmentEntity();
    enrollmentEntity.setId(1L);

    DocumentEntity documentEntity = new DocumentEntity();
    documentEntity.setId(1L);
    documentEntity.setDocumentType(DocType.OT);
    documentEntity.setDocumentName("Document Name");
    documentEntity.setDocumentUrl("Document URL");
    documentEntity.setEnrollment(enrollmentEntity);

    DocumentDto documentDto = DocumentMapper.convertToDto(documentEntity);
    assertNotNull(documentDto);
    assertEquals(documentEntity.getId(), documentDto.id());
    assertEquals(documentEntity.getDocumentType(), documentDto.documentType());
    assertEquals(documentEntity.getDocumentName(), documentDto.documentName());
  }

  @Test
  void testConvertToEntity() {
    EnrollmentEntity enrollmentEntity = new EnrollmentEntity();
    enrollmentEntity.setId(1L);

    long timestamp = 856332114336L;
    String documentUrlPostfix =
        "grades_" + TestProvider.provideStudentDto().person().idNumber() + "_" + timestamp + ".pdf";

    DocumentDto documentDto =
        new DocumentDto(1L, documentUrlPostfix, DocType.OT, "Documento de notas");

    DocumentEntity documentEntity = DocumentMapper.convertToEntity(documentDto);
    assertNotNull(documentEntity);
    assertEquals(documentDto.id(), documentEntity.getId());
    assertEquals(documentDto.documentType(), documentEntity.getDocumentType());
    assertEquals(documentDto.documentName(), documentEntity.getDocumentName());
  }
}
