package cr.co.ctpcit.citsacbackend.logic.services.storage;

import cr.co.ctpcit.citsacbackend.data.entities.inscriptions.DocumentEntity;
import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import cr.co.ctpcit.citsacbackend.data.repositories.inscriptions.DocumentRepository;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.DocumentDto;
import cr.co.ctpcit.citsacbackend.logic.exceptions.StorageException;
import cr.co.ctpcit.citsacbackend.logic.exceptions.StorageFileNotFoundException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Implementation of {@link StorageService} for file storage operations. Handles file storage,
 * retrieval, and deletion in the configured storage location.
 */
@RequiredArgsConstructor
@Service
public class StorageServiceImpl implements StorageService {
  @Value("${storage.inscriptions.documents.location}")
  private String location;

  private DocumentRepository documentRepository;

  /**
   * Constructs a new StorageServiceImpl with the required dependencies.
   *
   * @param documentRepository repository for document metadata operations
   */
  @Autowired
  public StorageServiceImpl(DocumentRepository documentRepository) {
    this.documentRepository = documentRepository;
  }

  /**
   * Initializes the storage directory. Creates the directory if it doesn't exist.
   *
   * @throws StorageException if the directory cannot be created
   */
  @PostConstruct
  @Override
  public void init() {
    try {
      // Create the storage directory if it doesn't exist
      File storageDir = new File(location);
      if (!storageDir.exists()) {
        Files.createDirectories(Paths.get(location));
      }
    } catch (FileAlreadyExistsException e) {
      // Do nothing
    } catch (IOException e) {
      throw new StorageException("Could not initialize storage", e);
    }
  }

  /**
   * Asynchronously stores a file in the storage location.
   *
   * @param file     the file to store
   * @param filename the name to give the stored file
   * @param docType  the type/category of the document
   * @return CompletableFuture containing DocumentDto with storage information
   * @throws StorageException if the file cannot be stored
   */
  @Async
  @Override
  public CompletableFuture<DocumentDto> store(MultipartFile file, String filename,
      DocType docType) {
    try {
      if (file != null) {
        Files.copy(file.getInputStream(), Paths.get(location).resolve(filename), REPLACE_EXISTING);
      }
    } catch (IOException e) {
      throw new StorageException(
          "Failed to store file " + file.getOriginalFilename() + " with name " + filename, e);
    }

    return CompletableFuture.completedFuture(new DocumentDto(null, filename, docType, null));
  }

  /**
   * Loads all stored files as Path objects. Currently returns empty stream.
   *
   * @return empty stream of Path objects
   */
  @Override
  public Stream<Path> loadAll() {
    return Stream.empty();
  }

  /**
   * Loads a stored document as a downloadable Resource.
   *
   * @param id the ID of the document to load
   * @return the file as a Resource
   * @throws ResponseStatusException      if document is not found in repository
   * @throws StorageFileNotFoundException if document file is not readable or doesn't exist
   */
  @Override
  public Resource loadAsResource(Long id) {
    DocumentEntity documentEntity = documentRepository.findById(id).orElseThrow(
        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            "Documento no encontrado con el id: " + id));

    try {
      Path file = Paths.get(documentEntity.getDocumentUrl());
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new StorageFileNotFoundException(
            "Documento no encontrado con el id: " + documentEntity.getId());

      }
    } catch (MalformedURLException e) {
      throw new StorageFileNotFoundException(
          "Documento no encontrado con el id: " + documentEntity.getId(), e);
    }
  }

  /**
   * Deletes all files in the storage location.
   *
   * @throws StorageException if files cannot be deleted
   */
  @Override
  public void deleteAll() {
    try (Stream<Path> paths = Files.walk(Paths.get(location))) {
      paths.filter(Files::isRegularFile).forEach(filePath -> {
        try {
          Files.delete(filePath);
        } catch (IOException e) {
          throw new StorageException("Failed to delete file " + filePath, e);
        }
      });
    } catch (IOException e) {
      throw new StorageException("Failed to delete all files", e);
    }
  }

  /**
   * Deletes a document by its URL postfix (relative path).
   *
   * @param urlPostfix the relative path of the document to delete
   * @throws StorageException if the file cannot be deleted
   */
  @Override
  public void deleteDocumentByUrlPostfix(String urlPostfix) {
    try {
      Files.deleteIfExists(Paths.get(location).resolve(urlPostfix));
    } catch (IOException e) {
      throw new StorageException("Failed to delete file " + urlPostfix, e);
    }
  }

  /**
   * Deletes a document by its full URL.
   *
   * @param url the full URL of the document to delete
   * @throws StorageException if the file cannot be deleted
   */
  @Override
  public void deleteDocumentByUrl(String url) {
    try {
      Files.deleteIfExists(Paths.get(url));
    } catch (IOException e) {
      throw new StorageException("Failed to delete file " + url, e);
    }
  }
}
