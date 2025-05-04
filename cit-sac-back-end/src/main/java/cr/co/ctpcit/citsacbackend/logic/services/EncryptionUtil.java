package cr.co.ctpcit.citsacbackend.logic.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

/**
 * Utility component for symmetric encryption and decryption using AES algorithm.
 * <p>
 * This component provides methods for encrypting and decrypting strings using AES-128
 * with a secret key configured through application properties. The encrypted results
 * are Base64 encoded for safe storage and transmission.
 * </p>
 *
 * <p><b>Security Notes:</b>
 * <ul>
 *   <li>Uses AES-128 (16 byte key) encryption in ECB mode (default for Java Cipher)</li>
 *   <li>Secret key is derived from application property 'encryption.secret'</li>
 *   <li>Automatically pads the secret to 16 bytes if shorter</li>
 *   <li>Throws runtime exceptions for cryptographic operations failures</li>
 * </ul>
 * </p>
 *
 * @see javax.crypto.Cipher
 */
@Component
public class EncryptionUtil {

  /**
   * The secret key used for encryption/decryption, injected from properties.
   */
  @Value("${encryption.secret}")
  private String secret;

  /**
   * The AES secret key specification derived from the secret.
   */
  private SecretKeySpec keySpec;

  /**
   * Initializes the AES key specification after bean construction.
   * <p>
   * Ensures the secret key is exactly 16 bytes long by either truncating
   * or padding the configured secret as needed.
   * </p>
   *
   * @throws IllegalStateException if UTF-8 encoding is not available
   */
  @PostConstruct
  public void init() {
    // Asegura una clave de 16 bytes para AES
    byte[] keyBytes = Arrays.copyOf(secret.getBytes(StandardCharsets.UTF_8), 16);
    keySpec = new SecretKeySpec(keyBytes, "AES");
  }

  /**
   * Encrypts the given plaintext string using AES.
   *
   * @param plainText the string to encrypt (UTF-8 encoded)
   * @return Base64 encoded encrypted string
   * @throws RuntimeException if encryption fails (wrapped crypto exception)
   * @throws IllegalArgumentException if plainText is null
   */

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

  /**
   * Decrypts a Base64 encoded ciphertext string.
   *
   * @param cipherText the Base64 encoded encrypted string
   * @return the decrypted original string (UTF-8 decoded)
   * @throws RuntimeException if decryption fails (wrapped crypto exception)
   * @throws IllegalArgumentException if cipherText is null or malformed
   * @throws IllegalArgumentException if the input is not valid Base64
   */
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
