package cr.co.ctpcit.citsacbackend.data.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileNameSanitizerTest {

  @Test
  void testSanitizeFileName_RemovesAccentsAndSpecialCharacters() {
    String questionText = "¿Cuál es la capital de España? ¡Vamos a aprender! á, é, í, ó, ú, ñ";
    String timeStamp = "1700000000000";

    String expectedFileName =
        "Cual_es_la_capital_de_Espana_Vamos_a_aprender_a_e_i_o_u_n-1700000000000";
    String actualFileName = FileNameSanitizer.sanitizeFileName(questionText, timeStamp);

    assertEquals(expectedFileName, actualFileName);
  }

  @Test
  void testSanitizeFileName_ReplacesSpacesWithUnderscores() {
    String questionText = "This is a test";
    String timeStamp = "1700000000000";

    String expectedFileName = "This_is_a_test-1700000000000";
    String actualFileName = FileNameSanitizer.sanitizeFileName(questionText, timeStamp);

    assertEquals(expectedFileName, actualFileName);
  }

  @Test
  void testSanitizeFileName_RemovesSpecialCharacters() {
    String questionText = "Hello!@#$%^&*()+=[]{}|;:',<>?/";
    String timeStamp = "1700000000000";

    String expectedFileName = "Hello-1700000000000";
    String actualFileName = FileNameSanitizer.sanitizeFileName(questionText, timeStamp);

    assertEquals(expectedFileName, actualFileName);
  }

  @Test
  void testSanitizeFileName_HandlesEmptyString() {
    String questionText = "";
    String timeStamp = "1700000000000";

    String expectedFileName = "-1700000000000";
    String actualFileName = FileNameSanitizer.sanitizeFileName(questionText, timeStamp);

    assertEquals(expectedFileName, actualFileName);
  }

  @Test
  void testSanitizeFileName_HandlesOnlyNumbers() {
    String questionText = "1234567890";
    String timeStamp = "1700000000000";

    String expectedFileName = "1234567890-1700000000000";
    String actualFileName = FileNameSanitizer.sanitizeFileName(questionText, timeStamp);

    assertEquals(expectedFileName, actualFileName);
  }

  @Test
  void testSanitizeFileName_HandlesMixOfValidAndInvalidCharacters() {
    String questionText = "Math: 2 + 2 = 4!";
    String timeStamp = "1700000000000";

    String expectedFileName = "Math_2_2_4-1700000000000";
    String actualFileName = FileNameSanitizer.sanitizeFileName(questionText, timeStamp);

    assertEquals(expectedFileName, actualFileName);
  }
}
