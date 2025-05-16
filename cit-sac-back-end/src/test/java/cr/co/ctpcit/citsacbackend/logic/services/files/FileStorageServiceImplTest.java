package cr.co.ctpcit.citsacbackend.logic.services.files;

import cr.co.ctpcit.citsacbackend.data.utils.FileNameSanitizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileStorageServiceImplTest {

  private FileStorageServiceImpl fileStorageService;

  @BeforeEach
  void setUp() {
    fileStorageService = new FileStorageServiceImpl();
    ReflectionTestUtils.setField(fileStorageService, "baseUploadDir", "C:/temp/spring/uploads/");
  }

  @Test
  void testStoreFile_Success() throws Exception {
    MultipartFile file = mock(MultipartFile.class);
    when(file.isEmpty()).thenReturn(false);
    when(file.getOriginalFilename()).thenReturn("test.txt");
    when(file.getInputStream()).thenReturn(InputStream.nullInputStream());

    String unsanitized = "unsafe_file_name";
    String category = "documents";

    try (MockedStatic<FileNameSanitizer> mockedSanitizer = mockStatic(FileNameSanitizer.class);
        MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
      String sanitizedFileName = "safe_file_name";
      mockedSanitizer.when(() -> FileNameSanitizer.sanitizeFileName(eq(unsanitized), anyString()))
          .thenReturn(sanitizedFileName);

      Path mockPath = Paths.get("C:/temp/spring/uploads/", category, sanitizedFileName + ".txt");
      mockedFiles.when(() -> Files.exists(any(Path.class))).thenReturn(false);
      mockedFiles.when(() -> Files.createDirectories(any(Path.class))).thenReturn(mockPath);
      mockedFiles.when(() -> Files.copy(any(InputStream.class), any(Path.class))).thenReturn(0L);

      String result = fileStorageService.storeFile(file, unsanitized, category);

      assertNotNull(result);
      assertTrue(result.contains("/api/files/" + category + "/" + sanitizedFileName));
    }
  }

  @Test
  void testStoreFile_EmptyFileThrowsException() {
    MultipartFile file = mock(MultipartFile.class);
    when(file.isEmpty()).thenReturn(true);

    Exception exception = assertThrows(IllegalArgumentException.class,
        () -> fileStorageService.storeFile(file, "unsafe", "documents"));

    assertEquals("File is empty", exception.getMessage());
  }

  @Test
  void testGetFileExtension_ValidFileName() {
    assertEquals("txt", fileStorageService.getFileExtension("document.txt"));
    assertEquals("jpg", fileStorageService.getFileExtension("image.jpg"));
  }

  @Test
  void testGetFileExtension_NoExtension() {
    assertEquals("dat", fileStorageService.getFileExtension("fileWithoutExtension"));
  }

  @Test
  void testGetFileExtension_NullFileName() {
    assertEquals("dat", fileStorageService.getFileExtension(null));
  }
}
