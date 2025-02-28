package cr.co.ctpcit.citsacbackend.rest.inscriptions;

import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class InscriptionsFileVerifier {

  public static void verifyFile(MultipartFile file) {
    if (file != null) {
      if (!Objects.requireNonNull(file.getOriginalFilename()).toLowerCase().endsWith(".pdf")) {
        throw new IllegalArgumentException("El archivo debe ser un PDF");
      }
    }
  }
}
