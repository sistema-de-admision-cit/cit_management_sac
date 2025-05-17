package cr.co.ctpcit.citsacbackend.logic.services.files;

import cr.co.ctpcit.citsacbackend.data.utils.FileNameSanitizer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
/**
 * Implementation of {@link FileStorageService} that handles file storage operations.
 * This service manages file uploads, including directory creation, filename sanitization,
 * and file extension handling.
 */
@Service
public class FileStorageServiceImpl implements FileStorageService {
  /** Base directory where uploaded files will be stored */
  @Value("${storage.questions.images.location}")
  private String baseUploadDir;

  @PostConstruct
  public void init() {
    //Create directory if it doesn't exist
    if(!Files.exists(Paths.get(baseUploadDir))) {
      try {
        Files.createDirectories(Paths.get(baseUploadDir));
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

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

    // Obtener el nombre original o usar un nombre por defecto
    String originalFilename = StringUtils.hasText(file.getOriginalFilename())
            ? file.getOriginalFilename()
            : "file_" + System.currentTimeMillis();

    // Obtener la extensión del archivo
    String fileExtension = getFileExtension(file); // Nuevo método mejorado

    Path uploadPath = Paths.get(baseUploadDir, category);
    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    String timeStamp = String.valueOf(System.currentTimeMillis());
    String uniqueFileName = FileNameSanitizer.sanitizeFileName(unsanitized, timeStamp);
    String fileName = uniqueFileName + "." + fileExtension;
    Path filePath = uploadPath.resolve(fileName);

    // Copiar el archivo reemplazando si existe
    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

    return "/api/files/" + category + "/" + fileName;
  }
  /**
   * Extracts the file extension from a filename.
   * Returns "dat" as default extension if no extension is found.
   *
   * @param file the name of the file including extension
   * @return the file extension (without dot), or "dat" if no extension found
   */
  @Override
  public String getFileExtension(MultipartFile file) {
    // Primero intentar con el nombre del archivo
    String originalFilename = file.getOriginalFilename();
    if (StringUtils.hasText(originalFilename) && originalFilename.contains(".")) {
      String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
      if (!extension.isEmpty()) {
        return extension.toLowerCase();
      }
    }

    String contentType = file.getContentType();
    if (contentType != null) {
      switch (contentType) {
        case "image/jpeg": return "jpg";
        case "image/png": return "png";
        case "image/gif": return "gif";
        case "application/pdf": return "pdf";
      }
    }
    return "dat";
  }
}
