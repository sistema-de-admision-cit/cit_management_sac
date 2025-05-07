package cr.co.ctpcit.citsacbackend.logic.services.files;

import cr.co.ctpcit.citsacbackend.data.utils.FileNameSanitizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
/**
 * Implementation of {@link FileStorageService} that handles file storage operations.
 * This service manages file uploads, including directory creation, filename sanitization,
 * and file extension handling.
 */
@Service
public class FileStorageServiceImpl implements FileStorageService {
  /** Base directory where uploaded files will be stored */
  @Value("${app.upload.base-dir}")
  private String baseUploadDir;
  /**
   * Stores a file in the specified category directory after performing validation,
   * sanitization, and generating a unique filename.
   *
   * @param file the multipart file to be stored
   * @param unsanitized the original unsanitized filename or identifier
   * @param category the category/directory where the file should be stored
   * @return the relative path or URL where the file can be accessed
   * @throws Exception if file is empty, directory creation fails, or file copy fails
   * @throws IllegalArgumentException if the provided file is empty
   */
  @Override
  public String storeFile(MultipartFile file, String unsanitized, String category) throws Exception {
    if (file.isEmpty()) {
      throw new IllegalArgumentException("File is empty");
    }

    String originalFilename = file.getOriginalFilename();
    String fileExtension = getFileExtension(originalFilename);

    // Usa la ruta absoluta configurada
    Path uploadPath = Paths.get(baseUploadDir, category);
    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    String timeStamp = String.valueOf(new Date().getTime());
    String uniqueFileName = FileNameSanitizer.sanitizeFileName(unsanitized, timeStamp);
    String fileName = uniqueFileName + "." + fileExtension;
    Path filePath = uploadPath.resolve(fileName);

    Files.copy(file.getInputStream(), filePath);

    // La ruta para acceder desde frontend (debes servirla luego)
    return "/api/files/" + category + "/" + fileName;
  }
  /**
   * Extracts the file extension from a filename.
   * Returns "dat" as default extension if no extension is found.
   *
   * @param fileName the name of the file including extension
   * @return the file extension (without dot), or "dat" if no extension found
   */
  @Override
  public String getFileExtension(String fileName) {
    if (fileName == null || !fileName.contains(".")) {
      return "dat";
    }
    return fileName.substring(fileName.lastIndexOf(".") + 1);
  }
}
