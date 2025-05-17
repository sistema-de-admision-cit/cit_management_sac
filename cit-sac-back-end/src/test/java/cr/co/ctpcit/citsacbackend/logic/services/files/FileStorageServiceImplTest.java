package cr.co.ctpcit.citsacbackend.logic.services.files;

import cr.co.ctpcit.citsacbackend.data.utils.FileNameSanitizer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileStorageServiceImplTest {

  @Mock
  private MultipartFile multipartFile;


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
  void testGetFileExtension_ValidFileName() throws Exception {
    MockMultipartFile file = new MockMultipartFile(
            "file",
            "document.txt",
            "text/plain",
            "content".getBytes()
    );
    assertEquals("txt", fileStorageService.getFileExtension(file));
  }

  @Test
  void testGetFileExtension_ImageJpg() throws Exception {
    MockMultipartFile file = new MockMultipartFile(
            "file",
            "image.jpg",
            "image/jpeg",
            "content".getBytes()
    );
    assertEquals("jpg", fileStorageService.getFileExtension(file));
  }

  @Test
  void testGetFileExtension_NoExtensionButContentType() throws Exception {
    MockMultipartFile file = new MockMultipartFile(
            "file",
            "fileWithoutExtension",
            "image/png",
            "content".getBytes()
    );
    assertEquals("png", fileStorageService.getFileExtension(file));
  }

  @Test
  void testGetFileExtension_NoExtensionNoContentType() throws Exception {
    MockMultipartFile file = new MockMultipartFile(
            "file",
            "fileWithoutExtension",
            null,
            "content".getBytes()
    );
    assertEquals("dat", fileStorageService.getFileExtension(file));
  }

}
