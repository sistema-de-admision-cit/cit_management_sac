package cr.co.ctpcit.citsacbackend.logic.mappers.inscriptions;


import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.DocumentEntity;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.DocumentDto;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * Mapper for {@link DocumentEntity} This class is used to convert an {@link DocumentEntity} to an
 * {@link DocumentDto} and vice versa
 */
public class DocumentMapper {

  /**
   * Converts a {@link DocumentEntity} to a {@link DocumentDto}.
   *
   * @param documentEntity the {@link DocumentEntity} to be converted
   * @return a {@link DocumentDto} representing the converted data
   */
  public static DocumentDto convertToDto(DocumentEntity documentEntity) {
    return DocumentDto.builder().id(documentEntity.getId())
        .documentType(documentEntity.getDocumentType()).documentName(documentEntity.getDocumentName())
        .build();
  }

  /**
   * Converts a list of {@link DocumentEntity} objects to a list of {@link DocumentDto} objects.
   *
   * @param documents the list of {@link DocumentEntity} objects to be converted
   * @return a list of {@link DocumentDto} objects representing the converted data
   */
  public static @NotEmpty List<DocumentDto> convertToDtoList(List<DocumentEntity> documents) {
    return documents.stream().map(DocumentMapper::convertToDto).toList();
  }

  /**
   * Converts a {@link DocumentDto} to a {@link DocumentEntity}.
   *
   * @param document the {@link DocumentDto} to be converted
   * @return a {@link DocumentEntity} representing the converted data
   */
  public static DocumentEntity convertToEntity(DocumentDto document) {
    return DocumentEntity.builder().id(document.id()).documentType(document.documentType())
        .documentName(document.documentName()).build();
  }
}
