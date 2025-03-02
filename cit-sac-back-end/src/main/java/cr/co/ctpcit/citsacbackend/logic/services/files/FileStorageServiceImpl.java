package cr.co.ctpcit.citsacbackend.logic.services.files;

import cr.co.ctpcit.citsacbackend.data.utils.FileNameSanitizer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class FileStorageServiceImpl implements FileStorageService {

  private static final String BASE_UPLOAD_DIR = "uploads/";

  @Override
  public String storeFile(MultipartFile file, String unsanitized, String category)
      throws Exception {
    if (file.isEmpty()) {
      throw new IllegalArgumentException("File is empty");
    }

    // Determine file extension dynamically
    String originalFilename = file.getOriginalFilename();
    String fileExtension = getFileExtension(originalFilename);

    // Define the upload directory based on category (e.g., "questions", "documents")
    Path uploadPath = Paths.get(BASE_UPLOAD_DIR, category);
    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    // Generate a unique filename
    String timeStamp = String.valueOf(new Date().getTime());
    String uniqueFileName = FileNameSanitizer.sanitizeFileName(unsanitized, timeStamp);
    String fileName = uniqueFileName + "." + fileExtension;
    Path filePath = uploadPath.resolve(fileName);

    // Save the file
    Files.copy(file.getInputStream(), filePath);

    // Return the relative path or full URL (depending on implementation)
    return "/api/files/" + category + "/" + fileName;
  }

  @Override
  public String getFileExtension(String fileName) {
    if (fileName == null || !fileName.contains(".")) {
      return "dat"; // Default extension for unknown file types
    }
    return fileName.substring(fileName.lastIndexOf(".") + 1);
  }
}
