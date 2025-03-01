package cr.co.ctpcit.citsacbackend.logic.services.files;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
  String storeFile(MultipartFile file, String unsanitized, String category) throws Exception;

  String getFileExtension(String fileName);
}
