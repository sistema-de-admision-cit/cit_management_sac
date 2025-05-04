package cr.co.ctpcit.citsacbackend.logic.services.storage;

import cr.co.ctpcit.citsacbackend.data.enums.DocType;
import cr.co.ctpcit.citsacbackend.logic.dto.inscriptions.DocumentDto;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
/**
 * Service interface for file storage operations.
 * Provides methods for initializing storage, storing files, loading resources,
 * and managing file deletions.
 */
@Service
public interface StorageService {
  /**
   * Initializes the storage service and prepares the storage location.
   * Typically, creates necessary directories if they don't exist.
   */
  void init();

  /**
   * Stores a file asynchronously with the given filename and document type.
   *
   * @param file the multipart file to store
   * @param filename the name to give to the stored file
   * @param docType the type/category of the document
   * @return a CompletableFuture containing the DocumentDto with storage information
   */
  CompletableFuture<DocumentDto> store(MultipartFile file, String filename, DocType docType);
  /**
   * Loads all stored files as Path objects.
   *
   * @return a stream of Path objects representing all stored files
   */
  Stream<Path> loadAll();
  /**
   * Loads a stored file as a downloadable Resource.
   *
   * @param id the identifier of the document to load
   * @return the file as a Resource object
   */
  Resource loadAsResource(Long id);
  /**
   * Deletes all stored files and clears the storage location.
   */
  void deleteAll();
  /**
   * Deletes a specific document by its URL postfix.
   *
   * @param urlPostfix the unique postfix portion of the document's URL
   */
  void deleteDocumentByUrlPostfix(String urlPostfix);
  /**
   * Deletes a specific document by its full URL.
   *
   * @param url the complete URL of the document to delete
   */
  void deleteDocumentByUrl(String url);
}
