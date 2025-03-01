package cr.co.ctpcit.citsacbackend.data.utils;

import java.text.Normalizer;

public class FileNameSanitizer {
  public static String sanitizeFileName(String text, String timeStamp) {
    // Normalize and remove accents
    String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
    String withoutAccents = normalized.replaceAll("\\p{M}", "");

    // Remove special characters except letters, numbers, and spaces
    String cleaned = withoutAccents.replaceAll("[^a-zA-Z0-9\\s]", "");

    // Replace spaces with underscores
    cleaned = cleaned.replaceAll("\\s+", "_");

    return cleaned + "-" + timeStamp;
  }
}
