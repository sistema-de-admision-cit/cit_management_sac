package cr.co.ctpcit.citsacbackend.logic.mappers;

import cr.co.ctpcit.citsacbackend.data.entities.inscription.DocumentEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.inscription.DocumentDto;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * Mapper for {@link DocumentEntity} This class is used to convert an {@link DocumentEntity} to an
 * {@link DocumentDto} and vice versa
 */
public class DocumentMapper {
  public static DocumentDto convertToDto(DocumentEntity documentEntity) {
    return DocumentDto.builder().id(documentEntity.getId())
        .documentType(documentEntity.getDocumentType())
        .documentName(documentEntity.getDocumentName()).documentUrl(documentEntity.getDocumentUrl())
        .build();
  }

  public static @NotEmpty List<DocumentDto> convertToDtoList(List<DocumentEntity> documents) {
    return documents.stream().map(DocumentMapper::convertToDto).toList();
  }
}
