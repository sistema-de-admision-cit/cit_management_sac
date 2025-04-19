package cr.co.ctpcit.citsacbackend.rest.inscriptions;

import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
/**
 * Utility class for verifying inscription file uploads.
 * Provides static methods to validate file properties for inscriptions.
 */
public class InscriptionsFileVerifier {
  /**
   * Verifies that the uploaded file is a PDF document.
   * Throws an IllegalArgumentException if the file is not null and doesn't have a PDF extension.
   *
   * @param file the MultipartFile to verify
   * @throws IllegalArgumentException if the file is not null and doesn't have a .pdf extension
   * @throws NullPointerException if the file's original filename is null
   */
  public static void verifyFile(MultipartFile file) {
    if (file != null) {
      if (!Objects.requireNonNull(file.getOriginalFilename()).toLowerCase().endsWith(".pdf")) {
        throw new IllegalArgumentException("El archivo debe ser un PDF");
      }
    }
  }
}
