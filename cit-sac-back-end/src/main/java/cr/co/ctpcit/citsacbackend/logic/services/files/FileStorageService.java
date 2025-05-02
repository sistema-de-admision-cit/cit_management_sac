package cr.co.ctpcit.citsacbackend.logic.services.files;

import org.springframework.web.multipart.MultipartFile;
/**
 * Service interface for file storage operations.
 * Defines methods for storing files and retrieving file extensions.
 */
public interface FileStorageService {
  /**
   * Stores a file with the given parameters.
   *
   * @param file The multipart file to be stored
   * @param unsanitized The unsanitized file name or path
   * @param category The category under which to store the file
   * @return A string representing the stored file location or identifier
   * @throws Exception if there's an error during file storage
   */
  String storeFile(MultipartFile file, String unsanitized, String category) throws Exception;

  /**
   * Retrieves the extension of a file from its name.
   *
   * @param fileName The name of the file including its extension
   * @return The file extension (without the dot), or empty string if no extension found
   */
  String getFileExtension(String fileName);
}
