package cr.co.ctpcit.citsacbackend.logic.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

@Component
public class EncryptionUtil {

  @Value("${encryption.secret}")
  private String secret;

  private SecretKeySpec keySpec;

  @PostConstruct
  public void init() {
    // Asegura una clave de 16 bytes para AES
    byte[] keyBytes = Arrays.copyOf(secret.getBytes(StandardCharsets.UTF_8), 16);
    keySpec = new SecretKeySpec(keyBytes, "AES");
  }

  public String encrypt(String plainText) {
    try {
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, keySpec);
      byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(encryptedBytes);
    } catch (Exception e) {
      throw new RuntimeException("Error al cifrar el texto", e);
    }
  }

  public String decrypt(String cipherText) {
    try {
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.DECRYPT_MODE, keySpec);
      byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
      byte[] decryptedBytes = cipher.doFinal(decodedBytes);
      return new String(decryptedBytes, StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new RuntimeException("Error al descifrar el texto", e);
    }
  }
}
